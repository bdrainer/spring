package org.bwg.rxmongodb.adapter.repository.inmemory;

import org.bwg.rxmongodb.model.profile.Profile;
import org.bwg.rxmongodb.model.profile.ProfileType;
import org.bwg.rxmongodb.port.ProfileRepository;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InMemoryProfileRepository implements ProfileRepository {

    private static final Map<String, Profile> DATABASE = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Mono<Profile> save(String username, Profile profile) {
        DATABASE.replace(toKey(username, profile), profile);
        return Mono.just(profile);
    }

    @Override
    public Mono<Profile> findByUniqueKey(String username, ProfileType type, String userkey) {
        String key = toKey(username, type, userkey);
        return DATABASE.containsKey(key) ? Mono.just(DATABASE.get(key)) : Mono.empty();
    }

    private String toKey(String username, Profile profile) {
        return String.join("#",
                username.toUpperCase(), profile.type().name(), profile.getUserkey().toUpperCase());
    }

    private String toKey(String username, ProfileType type, String userkey) {
        return String.join("#",
                username.toUpperCase(), type.name(), userkey.toUpperCase());
    }
}
