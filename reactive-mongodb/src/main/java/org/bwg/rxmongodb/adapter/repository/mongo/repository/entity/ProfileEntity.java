package org.bwg.rxmongodb.adapter.repository.mongo.repository.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "Profiles")
public class ProfileEntity {

    @Id
    private ProfileId id;

    private org.bson.Document profile;
}
