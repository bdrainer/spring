package com.bwg.app.repository.dynamo.entity.converter;

import com.bwg.app.model.profile.Profile;

public class ProfileConverter extends JacksonAttributeConverter<Profile>{
    public ProfileConverter() {
        super(Profile.class);
    }
}
