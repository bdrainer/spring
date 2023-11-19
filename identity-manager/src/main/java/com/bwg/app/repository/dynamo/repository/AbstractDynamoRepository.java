package com.bwg.app.repository.dynamo.repository;

import com.bwg.app.common.exception.ResourceAlreadyExistsException;
import com.bwg.app.common.exception.ResourceNotFoundException;
import com.bwg.app.repository.dynamo.entity.CompositeKey;
import com.bwg.app.repository.dynamo.entity.DynamoEntity;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.Optional;

public abstract class AbstractDynamoRepository<T extends DynamoEntity<CompositeKey>> implements DynamoRepository<T> {

    final DynamoDbClient client;

    final DynamoDbEnhancedClient enhancedClient;

    protected AbstractDynamoRepository(DynamoDbClient client, DynamoDbEnhancedClient enhancedClient) {
        this.client = client;
        this.enhancedClient = enhancedClient;
    }

    abstract DynamoDbTable<T> table();

    abstract String toPkString(String identifier);

    abstract String toSkString(String identifier);

    abstract void update(T source, T target);

    @Override
    public T insert(T entity) {
        T existingEntity = queryById(entity.getPk().toString(), entity.getSk().toString());
        if (existingEntity != null) {
            throw new ResourceAlreadyExistsException("Insert failed. Record already exists");
        }
        table().putItem(entity);
        return queryById(entity.getPk().toString(), entity.getSk().toString());
    }

    @Override
    public T update(T entity) {
        T existingEntity = queryById(entity.getPk().toString(), entity.getSk().toString());
        if (existingEntity == null) {
            throw new ResourceNotFoundException("Update failed. Record not found");
        }
        update(entity, existingEntity);
        return table().updateItem(existingEntity);
    }

    @Override
    public Optional<T> findById(String pkIdentifier, String skIdentifier) {
        return Optional.ofNullable(queryById(toPkString(pkIdentifier), toSkString(skIdentifier)));
    }

    /**
     * The composite key string is expected for the arguments, so in the form of PREFIX#IDENTIFIER
     */
    T queryById(String pk, String sk) {
        return table().getItem(b -> b.key(k -> k.partitionValue(pk).sortValue(sk)));
    }
}
