package com.bwg.app.repository.dynamo.entity.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.io.UncheckedIOException;
import java.time.format.DateTimeFormatter;

public class JacksonAttributeConverter<T> implements AttributeConverter<T> {

    private static final LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER =
            new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME);

    private static final ObjectMapper mapper = new ObjectMapper();

    private final Class<T> clazz;

    static {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LOCAL_DATETIME_SERIALIZER);

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(module);
    }

    public JacksonAttributeConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public AttributeValue transformFrom(T input) {
        try {
            return AttributeValue
                    .builder()
                    .s(mapper.writeValueAsString(input))
                    .build();
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException("Unable to serialize object", e);
        }
    }

    @Override
    public T transformTo(AttributeValue input) {
        try {
            return mapper.readValue(input.s(), this.clazz);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException("Unable to parse object", e);
        }
    }

    @Override
    public EnhancedType<T> type() {
        return EnhancedType.of(this.clazz);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
}
