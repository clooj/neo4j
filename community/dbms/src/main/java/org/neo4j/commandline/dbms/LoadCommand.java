/*
 * Copyright (c) 2002-2019 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.commandline.dbms;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.neo4j.cli.AbstractCommand;
import org.neo4j.cli.CommandFailedException;
import org.neo4j.cli.ExecutionContext;
import org.neo4j.configuration.Config;
import org.neo4j.configuration.GraphDatabaseSettings;
import org.neo4j.configuration.LayoutConfig;
import org.neo4j.dbms.archive.IncorrectFormat;
import org.neo4j.dbms.archive.Loader;
import org.neo4j.io.layout.DatabaseLayout;

import static java.util.Objects.requireNonNull;
import static org.neo4j.commandline.Util.checkLock;
import static org.neo4j.commandline.Util.wrapIOException;
import static org.neo4j.configuration.GraphDatabaseSettings.databases_root_path;
import static org.neo4j.io.fs.FileUtils.deleteRecursively;
import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

@Command(
        name = "load",
        header = "Load a database from an archive created with the dump command.",
        description = "Load a database from an archive. <archive-path> must be an archive created with the dump " +
                "command. <database> is the name of the database to create. Existing databases can be replaced " +
                "by specifying --force. It is not possible to replace a database that is mounted in a running " +
                "Neo4j server."

)
class LoadCommand extends AbstractCommand
{
    @Option( names = "--from", required = true, paramLabel = "<path>", description = "Path to archive created with the dump command." )
    private Path from;
    @Option( names = "--database", description = "Name of database.", defaultValue = GraphDatabaseSettings.DEFAULT_DATABASE_NAME )
    private String database;
    @Option( names = "--force", arity = "0", description = "If an existing database should be replaced." )
    private boolean force;

    private final Loader loader;

    LoadCommand( ExecutionContext ctx, Loader loader )
    {
        super( ctx );
        this.loader = requireNonNull( loader );
    }

    @Override
    protected void execute()
    {
        Config config = buildConfig();

        DatabaseLayout databaseLayout = DatabaseLayout.of( config.get( databases_root_path ), LayoutConfig.of( config ), database );

        deleteIfNecessary( databaseLayout, force );
        load( from, databaseLayout );
    }

    private Config buildConfig()
    {
        return Config.fromFile( ctx.confDir().resolve( Config.DEFAULT_CONFIG_FILE_NAME ) )
                .withHome( ctx.homeDir() )
                .withConnectorsDisabled()
                .withNoThrowOnFileLoadFailure()
                .build();
    }

    private static void deleteIfNecessary( DatabaseLayout databaseLayout, boolean force )
    {
        try
        {
            if ( force )
            {
                checkLock( databaseLayout.getStoreLayout() );
                deleteRecursively( databaseLayout.databaseDirectory() );
                deleteRecursively( databaseLayout.getTransactionLogsDirectory() );
            }
        }
        catch ( IOException e )
        {
            wrapIOException( e );
        }
    }

    private void load( Path archive, DatabaseLayout databaseLayout )
    {
        try
        {
            loader.load( archive, databaseLayout );
        }
        catch ( NoSuchFileException e )
        {
            if ( Paths.get( e.getMessage() ).toAbsolutePath().equals( archive.toAbsolutePath() ) )
            {
                throw new CommandFailedException( "archive does not exist: " + archive, e );
            }
            wrapIOException( e );
        }
        catch ( FileAlreadyExistsException e )
        {
            throw new CommandFailedException( "database already exists: " + databaseLayout.getDatabaseName(), e );
        }
        catch ( AccessDeniedException e )
        {
            throw new CommandFailedException(
                    "you do not have permission to load a database -- is Neo4j running as a " + "different user?", e );
        }
        catch ( IOException e )
        {
            wrapIOException( e );
        }
        catch ( IncorrectFormat incorrectFormat )
        {
            throw new CommandFailedException( "Not a valid Neo4j archive: " + archive, incorrectFormat );
        }
    }
}
