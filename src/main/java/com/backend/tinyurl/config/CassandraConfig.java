package com.backend.tinyurl.config;

import com.datastax.oss.driver.api.core.*;
import org.springframework.context.annotation.*;

import java.net.*;

@Configuration
public class CassandraConfig {

    @Bean("cassandraSession")
    public CqlSession getCassandraSession() throws URISyntaxException {
        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress("cassandra", 9042))
                .withKeyspace("tiny_keyspace")
                .withLocalDatacenter("datacenter1")
                .build();
    }

}
