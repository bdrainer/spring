package com.bwg.app.repository.dynamo.entity;

import java.io.Serializable;

public interface DynamoEntity<ID extends Serializable> {

    ID getPk();

    ID getSk();
}
