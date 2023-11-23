package org.bwg.rxmongodb.adapter.repository.mongo.repository.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.bwg.rxmongodb.model.profile.Profile;

public class EntityMapper {

    public static Profile toProfile(ObjectMapper mapper, Document document) {
        try {
            return mapper.readValue(document.toJson(), Profile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Document toDocument(ObjectMapper mapper, Profile profile) {
        return Document.parse(toProfileString(mapper, profile));
    }

    public static String toProfileString(ObjectMapper mapper, Profile profile) {
        try {
            return mapper.writeValueAsString(profile);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
