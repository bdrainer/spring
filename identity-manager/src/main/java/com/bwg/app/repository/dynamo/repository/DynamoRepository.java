package com.bwg.app.repository.dynamo.repository;

import com.bwg.app.repository.dynamo.entity.CompositeKey;
import com.bwg.app.repository.dynamo.entity.DynamoEntity;

import java.util.Optional;

/**
 * The Dynamo repository layer manages the Dynamo entities.  It reads and writes
 * entities from DynamoDB.
 *
 * @param <T>
 */
public interface DynamoRepository<T extends DynamoEntity<CompositeKey>> {
    String TABLE_NAME = "GenericSingleTable";

    T insert(T entity);

    T update(T entity);

    Optional<T> findById(String pkIdentifier, String skIdentifier);

}
