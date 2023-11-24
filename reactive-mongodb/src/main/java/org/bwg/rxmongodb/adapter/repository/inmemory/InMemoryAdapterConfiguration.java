package org.bwg.rxmongodb.adapter.repository.inmemory;

import lombok.extern.slf4j.Slf4j;
import org.bwg.rxmongodb.port.ProfileRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "app.repository", name = "provider", havingValue = "inmemory", matchIfMissing = true)
public class InMemoryAdapterConfiguration {

    public InMemoryAdapterConfiguration() {
        log.info("[app.repository.provider] = inmemory");
    }

    @Bean
    public ProfileRepository profileRepository() {
        return new InMemoryProfileRepository();
    }
}
