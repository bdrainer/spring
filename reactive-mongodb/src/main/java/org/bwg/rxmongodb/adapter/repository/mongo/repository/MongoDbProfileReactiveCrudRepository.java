package org.bwg.rxmongodb.adapter.repository.mongo.repository;

import org.bwg.rxmongodb.adapter.repository.mongo.repository.entity.ProfileEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MongoDbProfileReactiveCrudRepository extends ReactiveCrudRepository<ProfileEntity, String> {

    Mono<ProfileEntity> findByUsernameAndTypeAndUserkey(String username, String type, String userkey);
}
