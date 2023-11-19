package com.bwg.app.model.profile;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class GamerProfile extends Profile {

    String tag;

    String system;

    String credits;

    @Override
    public ProfileType type() {
        return ProfileType.GAMER;
    }
}
