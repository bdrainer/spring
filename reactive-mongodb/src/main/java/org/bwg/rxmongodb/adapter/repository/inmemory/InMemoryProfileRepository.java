package org.bwg.rxmongodb.adapter.repository.inmemory;

import org.bwg.rxmongodb.model.exception.RecordNotFoundException;
import org.bwg.rxmongodb.model.profile.Profile;
import org.bwg.rxmongodb.model.profile.ProfileType;
import org.bwg.rxmongodb.port.ProfileRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores profiles in memory.  Intended for local development only.
 */
public class InMemoryProfileRepository implements ProfileRepository {

    private final Map<String, Profile> DATABASE = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Mono<Profile> save(String username, Profile profile) {
        DATABASE.put(toKey(username, profile), profile);
        return Mono.just(profile);
    }

    @Override
    public Mono<Profile> findByUniqueKey(String username, ProfileType type, String userkey) {
        String key = toKey(username, type, userkey);
        return DATABASE.containsKey(key)
                ? Mono.just(DATABASE.get(key))
                : Mono.error(new RecordNotFoundException("Profile not found"));
    }

    @Override
    public Flux<Profile> findByUsername(String username) {
        return null;
    }

    @Override
    public Flux<Profile> findByUsernameAndType(String username, ProfileType type) {
        return null;
    }

    // ------------------------------------------------------------------------

    private String toKey(String username, Profile profile) {
        return String.join("#",
                username.toUpperCase(), profile.type().name(), profile.getUserkey().toUpperCase());
    }

    private String toKey(String username, ProfileType type, String userkey) {
        return String.join("#",
                username.toUpperCase(), type.name(), userkey.toUpperCase());
    }
}
