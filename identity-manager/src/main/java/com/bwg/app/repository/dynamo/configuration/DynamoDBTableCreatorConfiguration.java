package com.bwg.app.repository.dynamo.configuration;

import com.bwg.app.repository.dynamo.entity.CompositeKey;
import com.bwg.app.repository.dynamo.entity.converter.CompositeKeyConverter;
import com.bwg.app.repository.dynamo.repository.DynamoRepository;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;


/**
 * Configuration for creating the DynamoDB table.  The intention of this class is to be used for local development only.
 * Non-local environments will use Terraform or the AWS console.  It is expected non-local environments have an existing
 * DynamoDB table and this configuration will never be enabled.  If it were enabled in a non-local environment and the
 * DynamoDB table existed this configuration would have no affect.  If the table did not exist this configuration will
 * try to create it, it might fail from permissions, or it may succeed in which case the rcu and wcu properties will be
 * used.
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "dynamodb.table.create.enabled", havingValue = "true")
public class DynamoDBTableCreatorConfiguration {

    @Value("${dynamodb.table.rcu}")
    private Long readCapacityUnits;

    @Value("${dynamodb.table.wcu}")
    private Long writeCapacityUnits;

    private final DynamoDbEnhancedClient enhancedClient;

    private final TableSchema<DynamoTableDefinition> tableSchema;

    public DynamoDBTableCreatorConfiguration(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
        this.tableSchema = TableSchema.fromClass(DynamoTableDefinition.class);
    }

    @PostConstruct
    public void initialize() {
        log.info("Creating table {}", DynamoRepository.TABLE_NAME);
        try {
            enhancedClient.table(DynamoRepository.TABLE_NAME, tableSchema).createTable(builder ->
                    builder.provisionedThroughput(p ->
                            p.readCapacityUnits(readCapacityUnits).writeCapacityUnits(writeCapacityUnits)
                    )
            );
        } catch (ResourceInUseException e) {
            log.info("table {} already exists", DynamoRepository.TABLE_NAME);
        }
    }

    @Setter
    @DynamoDbBean
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    public static class DynamoTableDefinition {

        @Getter(onMethod_ = {@DynamoDbPartitionKey, @DynamoDbAttribute("pk"),
                @DynamoDbConvertedBy(CompositeKeyConverter.class)})
        protected CompositeKey pk; // partition key

        @Getter(onMethod_ = {@DynamoDbSortKey, @DynamoDbAttribute("sk"),
                @DynamoDbConvertedBy(CompositeKeyConverter.class)})
        protected CompositeKey sk; // sort key
    }
}
