package com.bwg.app.repository.dynamo.configuration;

import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

/**
 * Provides the beans required to interact with DynamoDB.
 */
@Configuration
public class DynamoDBClientConfiguration {

    @Setter(AccessLevel.PACKAGE)
    @Value("${amazon.region}")
    private String region;

    @Value("${amazon.dynamodb.endpoint}")
    private String endpoint;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return StringUtils.hasText(endpoint)
                ? withBrokerUrlOverride()
                : DynamoDbClient.builder().credentialsProvider(DefaultCredentialsProvider.create())
                        .region(Region.of(region)).build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient()).build();
    }

    private DynamoDbClient withBrokerUrlOverride() {
        return DynamoDbClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .endpointOverride(URI.create(endpoint))
                .region(Region.of(region))
                .build();
    }

}
