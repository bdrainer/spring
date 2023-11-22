package com.bwg.app.model.profile;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalTime;

@Value
@Jacksonized
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EmploymentProfile extends Profile {

    String company;

    String title;

    String start;

    String end;

    @Override
    public ProfileType type() {
        return ProfileType.EMPLOYMENT;
    }
}
