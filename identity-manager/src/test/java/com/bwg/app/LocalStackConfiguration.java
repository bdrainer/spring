package com.bwg.app;

import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.DYNAMODB;

@TestConfiguration
public class LocalStackConfiguration {
    static LocalStackContainer localStack =
            new LocalStackContainer(DockerImageName.parse("localstack/localstack:1.0.4.1.nodejs18"))
                    .withServices(DYNAMODB)
                    .withNetworkAliases("localstack");

    static {
        localStack.start();
        System.setProperty("aws.accessKeyId", "test");
        System.setProperty("aws.secretAccessKey", "test");
        System.setProperty("aws.sessionToken", "test");
        System.setProperty("amazon.dynamodb.endpoint", localStack.getEndpointOverride(DYNAMODB).toString());
    }
}
