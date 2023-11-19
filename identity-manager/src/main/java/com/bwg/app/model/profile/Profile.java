package com.bwg.app.model.profile;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ContactProfile.class, name = "CONTACT"),
        @JsonSubTypes.Type(value = AddressProfile.class, name = "ADDRESS"),
        @JsonSubTypes.Type(value = GamerProfile.class, name = "GAMER")
})
@Getter
@SuperBuilder
public abstract class Profile {

    String userkey;

    /**
     * We want to force the casing of the user key to upper
     * case.  It will be stored this way and form part of a
     * unique key.
     */
    public void userKeyToUpper() {
        userkey = userkey !=  null ? userkey.toUpperCase() : null;
    }

    public abstract ProfileType type();

}
