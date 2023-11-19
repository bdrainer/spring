package com.bwg.app.repository.dynamo.entity.converter;

import com.bwg.app.repository.dynamo.entity.CompositeKey;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class CompositeKeyConverter implements AttributeConverter<CompositeKey> {

    @Override
    public AttributeValue transformFrom(CompositeKey input) {
        return AttributeValue.builder().s(input.toString()).build();
    }

    @Override
    public CompositeKey transformTo(AttributeValue input) {
        String[] parts = input.s().split(CompositeKey.DELIMITER);
        return parts.length == 3
                ? new CompositeKey(parts[0], parts[1], parts[2])
                : new CompositeKey(parts[0], parts[1]);
    }

    @Override
    public EnhancedType<CompositeKey> type() {
        return EnhancedType.of(CompositeKey.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
}
