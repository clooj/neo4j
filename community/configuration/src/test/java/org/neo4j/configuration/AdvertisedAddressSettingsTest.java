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
package org.neo4j.configuration;

import org.junit.jupiter.api.Test;

import java.util.Map;

import org.neo4j.graphdb.config.Setting;
import org.neo4j.internal.helpers.AdvertisedSocketAddress;
import org.neo4j.internal.helpers.ListenSocketAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.neo4j.configuration.Settings.advertisedAddress;
import static org.neo4j.configuration.Settings.listenAddress;
import static org.neo4j.internal.helpers.collection.MapUtil.stringMap;

class AdvertisedAddressSettingsTest
{
    private static final Setting<ListenSocketAddress> listen_address_setting = listenAddress( "listen_address", 1234 );
    private static final Setting<AdvertisedSocketAddress> advertised_address_setting =
            advertisedAddress( "advertised_address", listen_address_setting );

    @Test
    void shouldParseExplicitSettingValueWhenProvided()
    {
        // given
        Map<String, String> config = stringMap(
                GraphDatabaseSettings.default_advertised_address.name(), "server1.example.com",
                advertised_address_setting.name(), "server1.internal:4000" );

        // when
        AdvertisedSocketAddress advertisedSocketAddress = advertised_address_setting.apply( config::get );

        // then
        assertEquals( "server1.internal", advertisedSocketAddress.getHostname() );
        assertEquals( 4000, advertisedSocketAddress.getPort() );
    }

    @Test
    void shouldCombineDefaultHostnameWithPortFromListenAddressSettingWhenNoValueProvided()
    {
        // given
        Map<String, String> config = stringMap(
                GraphDatabaseSettings.default_advertised_address.name(), "server1.example.com" );

        // when
        AdvertisedSocketAddress advertisedSocketAddress = advertised_address_setting.apply( config::get );

        // then
        assertEquals( "server1.example.com", advertisedSocketAddress.getHostname() );
        assertEquals( 1234, advertisedSocketAddress.getPort() );
    }

    @Test
    void shouldCombineDefaultHostnameWithExplicitPortWhenOnlyAPortProvided()
    {
        // given
        Map<String, String> config = stringMap(
                GraphDatabaseSettings.default_advertised_address.name(), "server1.example.com",
                advertised_address_setting.name(), ":4000" );

        // when
        AdvertisedSocketAddress advertisedSocketAddress = advertised_address_setting.apply( config::get );

        // then
        assertEquals( "server1.example.com", advertisedSocketAddress.getHostname() );
        assertEquals( 4000, advertisedSocketAddress.getPort() );
    }
}
