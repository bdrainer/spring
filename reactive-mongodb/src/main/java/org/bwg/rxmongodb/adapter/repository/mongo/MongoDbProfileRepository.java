package org.bwg.rxmongodb.adapter.repository.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.bwg.rxmongodb.adapter.repository.mongo.repository.MongoDbProfileReactiveCrudRepository;
import org.bwg.rxmongodb.adapter.repository.mongo.repository.entity.EntityMapper;
import org.bwg.rxmongodb.adapter.repository.mongo.repository.entity.ProfileEntity;
import org.bwg.rxmongodb.adapter.repository.mongo.repository.entity.ProfileId;
import org.bwg.rxmongodb.model.exception.RecordNotFoundException;
import org.bwg.rxmongodb.model.profile.Profile;
import org.bwg.rxmongodb.model.profile.ProfileType;
import org.bwg.rxmongodb.port.ProfileRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class MongoDbProfileRepository implements ProfileRepository {

    private final ObjectMapper objectMapper;

    private final MongoDbProfileReactiveCrudRepository repository;

    @Override
    public Mono<Profile> save(String username, Profile profile) {
        return repository.save(ProfileEntity.builder()
                        .id(ProfileId.toId(username, profile.type(), profile.getUserkey()))
                        .profile(EntityMapper.toDocument(objectMapper, profile))
                        .build())
                .map(entity -> profile);
    }

    @Override
    public Mono<Profile> findByUniqueKey(String username, ProfileType type, String userkey) {
        return repository.findById(ProfileId.toId(username, type, userkey))
                .map(entity -> EntityMapper.toProfile(objectMapper, entity.getProfile()))
                .switchIfEmpty(Mono.error(new RecordNotFoundException("Profile not found")));
    }

    @Override
    public Flux<Profile> findByUsername(String username) {
        return repository.findByUsername(username.toUpperCase())
                .map(entity -> EntityMapper.toProfile(objectMapper, entity.getProfile()));
    }

    @Override
    public Flux<Profile> findByUsernameAndType(String username, ProfileType type) {
        return repository.findByUsernameAndType(username.toUpperCase(), type.name())
                .map(entity -> EntityMapper.toProfile(objectMapper, entity.getProfile()));
    }
}
