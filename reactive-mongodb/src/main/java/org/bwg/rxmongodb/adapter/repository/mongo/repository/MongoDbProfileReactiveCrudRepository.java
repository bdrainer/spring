package org.bwg.rxmongodb.adapter.repository.mongo.repository;

import org.bwg.rxmongodb.adapter.repository.mongo.repository.entity.ProfileEntity;
import org.bwg.rxmongodb.adapter.repository.mongo.repository.entity.ProfileId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDbProfileReactiveCrudRepository extends ReactiveCrudRepository<ProfileEntity, ProfileId> {

}
