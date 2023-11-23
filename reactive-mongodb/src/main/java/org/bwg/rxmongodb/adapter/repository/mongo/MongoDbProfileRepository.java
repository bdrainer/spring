package org.bwg.rxmongodb.adapter.repository.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.bwg.rxmongodb.adapter.repository.mongo.repository.MongoDbProfileReactiveCrudRepository;
import org.bwg.rxmongodb.adapter.repository.mongo.repository.entity.EntityMapper;
import org.bwg.rxmongodb.adapter.repository.mongo.repository.entity.ProfileEntity;
import org.bwg.rxmongodb.model.profile.Profile;
import org.bwg.rxmongodb.model.profile.ProfileType;
import org.bwg.rxmongodb.port.ProfileRepository;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class MongoDbProfileRepository implements ProfileRepository {

    private final ObjectMapper objectMapper;

    private final MongoDbProfileReactiveCrudRepository repository;

    @Override
    public Mono<Profile> save(String username, Profile profile) {
        return repository.save(
                ProfileEntity.builder()
                        .username(username.toUpperCase())
                        .type(profile.type().name())
                        .userkey(profile.getUserkey().toUpperCase())
                        .profile(EntityMapper.toDocument(objectMapper, profile))
                        .build()).map(entity -> profile);
    }

    @Override
    public Mono<Profile> findByUniqueKey(String username, ProfileType type, String userkey) {
        return repository.findByUsernameAndTypeAndUserkey(
                username.toUpperCase(), type.name(), userkey.toUpperCase())
                .map(e -> EntityMapper.toProfile(objectMapper, e.getProfile()));
    }
}
