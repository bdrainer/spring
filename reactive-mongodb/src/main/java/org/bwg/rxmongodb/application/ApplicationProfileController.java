package org.bwg.rxmongodb.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bwg.rxmongodb.model.profile.Profile;
import org.bwg.rxmongodb.model.profile.ProfileType;
import org.bwg.rxmongodb.port.ProfileRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ApplicationProfileController {

    private final ProfileRepository repository;

    @PutMapping("/{username}")
    public Mono<Profile> update(@PathVariable String username, @RequestBody Profile payload) {
        log.debug("Update profile for {}", username);
        return repository.save(username, payload);
    }

    @GetMapping(value = "/{username}/{type}/{userkey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Profile> getProfileByUniqueKey(@PathVariable String username, @PathVariable String type, @PathVariable String userkey) {
        log.debug("Get profile {}, {}, {}", username, type, userkey);
        return repository.findByUniqueKey(username, ProfileType.valueOf(type.toUpperCase()), userkey);
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Profile> getProfileByUsername(@PathVariable String username) {
        log.debug("Get profiles for user {}", username);
        return repository.findByUsername(username);
    }

    @GetMapping(value = "/{username}/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Profile> getProfileByUsernameAndType(@PathVariable String username, @PathVariable String type) {
        log.debug("Get profiles {} for user {}", type, username);
        return repository.findByUsernameAndType(username, ProfileType.valueOf(type.toUpperCase()));
    }

}
