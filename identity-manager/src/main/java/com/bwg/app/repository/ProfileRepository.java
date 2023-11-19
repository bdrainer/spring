package com.bwg.app.repository;

import com.bwg.app.model.profile.Profile;
import com.bwg.app.model.profile.ProfileType;

import java.util.List;
import java.util.Optional;

/**
 * Stores profile instances into a datastore.
 */
public interface ProfileRepository {
    Profile insert(String username, Profile profile);

    Profile update(String username, Profile profile);

    Optional<Profile> findById(String username, ProfileType type, String userkey);

    List<Profile> findByProfileType(String username, ProfileType type);
}
