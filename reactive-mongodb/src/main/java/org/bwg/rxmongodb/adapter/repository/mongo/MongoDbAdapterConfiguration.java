package org.bwg.rxmongodb.adapter.repository.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bwg.rxmongodb.adapter.repository.mongo.repository.MongoDbProfileReactiveCrudRepository;
import org.bwg.rxmongodb.port.ProfileRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Slf4j
@ComponentScan
@Configuration
@EnableReactiveMongoRepositories
@ConditionalOnProperty(prefix = "app.repository", name = "provider", havingValue = "mongodb")
public class MongoDbAdapterConfiguration {

    public MongoDbAdapterConfiguration() {
        log.info("[app.repository.provider] = mongodb");
    }

    @Bean
    public ProfileRepository profileRepository(ObjectMapper mapper, MongoDbProfileReactiveCrudRepository repository) {
        return new MongoDbProfileRepository(mapper, repository);
    }
}
