/*
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
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
package org.neo4j.index.gbptree;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Optional;

import org.neo4j.io.pagecache.PageCursor;

class TreeStatePair
{
    static Pair<TreeState,TreeState> readStatePages( PageCursor cursor, long pageIdA, long pageIdB ) throws IOException
    {
        TreeState stateA;
        TreeState stateB;

        // Use write lock to avoid should retry. Fine because there can be no concurrency at this point anyway

        PageCursorUtil.goTo( cursor, "state page A", pageIdA );
        stateA = TreeState.read( cursor );

        PageCursorUtil.goTo( cursor, "state page B", pageIdB );
        stateB = TreeState.read( cursor );
        return Pair.of( stateA, stateB );
    }

    static TreeState selectNewestValidState( Pair<TreeState,TreeState> states )
    {
        return selectNewestValidStateOptionally( states ).orElseThrow( () ->
                new TreeInconsistencyException( "Unexpected combination of state.%n  STATE_A[%s]%n  STATE_B[%s]",
                        states.getLeft(), states.getRight() ) );
    }

    static TreeState selectOldestOrInvalid( Pair<TreeState,TreeState> states )
    {
        TreeState newestValidState = selectNewestValidStateOptionally( states ).orElse( states.getRight() );
        return newestValidState == states.getLeft() ? states.getRight() : states.getLeft();
    }

    private static Optional<TreeState> selectNewestValidStateOptionally( Pair<TreeState,TreeState> states )
    {
        TreeState stateA = states.getLeft();
        TreeState stateB = states.getRight();

        if ( stateA.isValid() != stateB.isValid() )
        {
            // return only valid
            return stateA.isValid() ? Optional.of( stateA ) : Optional.of( stateB );
        }
        else if ( stateA.isValid() && stateB.isValid() )
        {
            // return newest

            // compare unstable generations of A/B and include sanity check for stable generations
            // such that there cannot be a state S compared to other state O where
            // S.unstableGeneration > O.unstableGeneration AND S.stableGeneration < O.stableGeneration

            if ( stateA.stableGeneration() >= stateB.stableGeneration() &&
                    stateA.unstableGeneration() > stateB.unstableGeneration() )
            {
                return Optional.of( stateA );
            }
            else if ( stateA.stableGeneration() <= stateB.stableGeneration() &&
                    stateA.unstableGeneration() < stateB.unstableGeneration() )
            {
                return Optional.of( stateB );
            }
        }

        // return null communicating that this combination didn't result in any valid "newest" state
        return Optional.empty();
    }
}
