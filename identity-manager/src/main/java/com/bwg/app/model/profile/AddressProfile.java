package com.bwg.app.model.profile;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AddressProfile extends Profile {

    String line1;

    String line2;

    String city;

    String state;

    String postalCode;
    
    boolean current;

    @Override
    public ProfileType type() {
        return ProfileType.ADDRESS;
    }
}
