package org.bwg.rxmongodb.model.profile;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ContactProfile.class, name = "CONTACT"),
        @JsonSubTypes.Type(value = AddressProfile.class, name = "ADDRESS"),
        @JsonSubTypes.Type(value = EmploymentProfile.class, name = "EMPLOYMENT")
})
@Getter
@SuperBuilder
public abstract class Profile {

    String uid;

    String userkey;

    public abstract ProfileType type();

}
