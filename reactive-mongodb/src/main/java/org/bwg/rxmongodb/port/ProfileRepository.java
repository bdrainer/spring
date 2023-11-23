package org.bwg.rxmongodb.port;

import org.bwg.rxmongodb.model.profile.Profile;
import org.bwg.rxmongodb.model.profile.ProfileType;
import reactor.core.publisher.Mono;

public interface ProfileRepository {

    Mono<Profile> save(String username, Profile profile);

    Mono<Profile> findByUniqueKey(String username, ProfileType type, String userkey);
}
