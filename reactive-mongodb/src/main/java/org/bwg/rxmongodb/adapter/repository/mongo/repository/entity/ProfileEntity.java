package org.bwg.rxmongodb.adapter.repository.mongo.repository.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "AccountProfiles")
@CompoundIndexes({
        @CompoundIndex(name = "profile_key", def = "{'username' : 1, 'type': 1, 'userkey' : 1}")
})
public class ProfileEntity {

    @Id
    private String id;

    private String username;

    private String type;

    private String userkey;

    private org.bson.Document profile;
}
