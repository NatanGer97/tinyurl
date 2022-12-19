package com.backend.tinyurl.config;

import com.datastax.oss.driver.api.core.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

import java.net.*;

@Configuration
public class CassandraConfig {
    @Value("${cassandra.host}")
    private String cassandraHost;

    @Value("${cassandra.keyspace}")
    private String keyspace;



    @Bean("cassandraSession")
    public CqlSession getCassandraSession() throws URISyntaxException {
        CqlSession cqlSession = CqlSession.builder()
//                .addContactPoint(new InetSocketAddress("cassandra", 9042))
                .addContactPoint(new InetSocketAddress(cassandraHost, 9042))
//                .withKeyspace("tiny_keyspace")
                .withLocalDatacenter("datacenter1")
                .build();

        boolean isKeyspaceNotExists =
                cqlSession.execute("SELECT keyspace_name FROM system_schema.keyspaces WHERE keyspace_name='" + keyspace + "';").all().isEmpty();
        if(isKeyspaceNotExists) {
            cqlSession.execute("CREATE KEYSPACE " + keyspace + " WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};");
        }

        cqlSession.close();

        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress(cassandraHost, 9042))
                .withKeyspace(keyspace)
                .withLocalDatacenter("datacenter1")
                .build();

    }



}
