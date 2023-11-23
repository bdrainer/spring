package org.bwg.rxmongodb.model.profile;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ContactProfile extends Profile {
    String firstname;
    String lastname;
    String email;
    String phone;
    @Override
    public ProfileType type() {
        return ProfileType.CONTACT;
    }
}
