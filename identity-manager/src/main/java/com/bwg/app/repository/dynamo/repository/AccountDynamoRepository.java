package com.bwg.app.repository.dynamo.repository;

import com.bwg.app.repository.dynamo.entity.AccountEntity;
import com.bwg.app.repository.dynamo.entity.CompositeKey;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Repository
public class AccountDynamoRepository extends AbstractDynamoRepository<AccountEntity> {

    private final TableSchema<AccountEntity> tableSchema;

    public AccountDynamoRepository(DynamoDbClient client, DynamoDbEnhancedClient enhancedClient) {
        super(client, enhancedClient);
        this.tableSchema = TableSchema.fromClass(AccountEntity.class);
    }

    @Override
    DynamoDbTable<AccountEntity> table() {
        return enhancedClient.table(TABLE_NAME, tableSchema);
    }

    @Override
    String toPkString(String identifier) {
        return new CompositeKey(AccountEntity.PK_PREFIX, identifier).toString();
    }

    @Override
    String toSkString(String identifier) {
        return new CompositeKey(AccountEntity.SK_PREFIX, identifier).toString();
    }

    @Override
    void update(AccountEntity source, AccountEntity target) {
        target.setAccount(source.getAccount());
    }
}
