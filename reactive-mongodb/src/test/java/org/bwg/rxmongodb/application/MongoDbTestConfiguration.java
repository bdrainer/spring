package org.bwg.rxmongodb.application;

import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.MongoDBContainer;

@TestConfiguration
public class MongoDbTestConfiguration {

    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest")
            .withExposedPorts(27017);

    static {
        mongoDBContainer.start();
        System.setProperty("spring.data.mongodb.uri", mongoDBContainer.getReplicaSetUrl());
    }
}
