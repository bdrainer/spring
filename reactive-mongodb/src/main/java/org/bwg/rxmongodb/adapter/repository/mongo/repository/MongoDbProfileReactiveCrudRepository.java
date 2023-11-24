package org.bwg.rxmongodb.adapter.repository.mongo.repository;

import org.bwg.rxmongodb.adapter.repository.mongo.repository.entity.ProfileEntity;
import org.bwg.rxmongodb.adapter.repository.mongo.repository.entity.ProfileId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MongoDbProfileReactiveCrudRepository extends ReactiveCrudRepository<ProfileEntity, ProfileId> {

    @Query("{ '_id.username' : ?0 }")
    Flux<ProfileEntity> findByUsername(String username);

    @Query("{ '_id.username' : ?0 , '_id.type' :  ?1 }")
    Flux<ProfileEntity> findByUsernameAndType(String username, String type);

}
