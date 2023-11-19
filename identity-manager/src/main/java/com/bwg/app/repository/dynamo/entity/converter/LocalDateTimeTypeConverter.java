package com.bwg.app.repository.dynamo.entity.converter;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.ZoneOffset.UTC;

public class LocalDateTimeTypeConverter implements AttributeConverter<LocalDateTime> {

    @Override
    public AttributeValue transformFrom(LocalDateTime input) {
        return AttributeValue.builder()
                .s(input.atZone(ZoneId.systemDefault()).withZoneSameInstant(UTC)
                        .format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }

    @Override
    public LocalDateTime transformTo(AttributeValue input) {
        return ZonedDateTime.parse(input.s(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    @Override
    public EnhancedType<LocalDateTime> type() {
        return EnhancedType.of(LocalDateTime.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
}
