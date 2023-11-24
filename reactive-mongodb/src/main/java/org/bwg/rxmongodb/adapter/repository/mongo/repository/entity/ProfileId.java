package org.bwg.rxmongodb.adapter.repository.mongo.repository.entity;

import lombok.Data;
import org.bwg.rxmongodb.model.profile.ProfileType;

import java.io.Serializable;

@Data
public class ProfileId implements Serializable {

    final String username;
    final String type;
    final String userkey;


    public static ProfileId toId(String username, ProfileType type, String userkey) {
        return new ProfileId(username.toUpperCase(), type.name(), userkey.toUpperCase());
    }
}
