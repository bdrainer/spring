package org.bwg.rxmongodb.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bwg.rxmongodb.model.profile.Profile;
import org.bwg.rxmongodb.model.profile.ProfileType;
import org.bwg.rxmongodb.port.ProfileRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class ApplicationProfileController {

    private final ProfileRepository repository;

    @PutMapping("/{username}/profiles")
    public Mono<Profile> update(@PathVariable String username, @RequestBody Profile payload) {
        log.info("Update profile for {}", username);
        return repository.save(username, payload);
    }

    @GetMapping("/{username}/profiles/{type}/{userkey}")
    public Mono<Profile> getProfileByUniqueKey(@PathVariable String username, @PathVariable String type, @PathVariable String userkey) {
        log.info("Get profile {}, {}, {}", username, type, userkey);
        return repository.findByUniqueKey(username, ProfileType.valueOf(type.toUpperCase()), userkey);
    }

}
