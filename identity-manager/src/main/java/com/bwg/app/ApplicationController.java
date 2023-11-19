package com.bwg.app;

import com.bwg.app.model.account.Account;
import com.bwg.app.model.profile.Profile;
import com.bwg.app.model.profile.ProfileType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class ApplicationController {

    private final ApplicationService service;

    @PostMapping("/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@PathVariable String username) {
        return service.createAccount(username);
    }

    @PutMapping(value = "/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Account updateAccount(@PathVariable String username, @RequestBody Account account) {
        return service.updateAccount(username, account);
    }

    @GetMapping("/{username}")
    public Account getAccount(@PathVariable String username) {
        return service.findAccount(username);
    }

    // ------------------------------------------------------------------------

    @PostMapping("/{username}/profiles")
    public Profile createProfile(@PathVariable String username, @RequestBody Profile profile) {
        return service.createProfile(username, profile);
    }

    @PutMapping("/{username}/profiles")
    public Profile updateProfile(@PathVariable String username, @RequestBody Profile profile) {
        return service.updateProfile(username, profile);
    }

    @GetMapping("/{username}/profiles/{profileType}/{userkey}")
    public Profile getProfile(@PathVariable String username,
                              @PathVariable String profileType,
                              @PathVariable String userkey) {
        return service.findProfile(username, ProfileType.valueOf(profileType.toUpperCase()), userkey.toUpperCase());
    }
    @GetMapping("/{username}/profiles/{profileType}")
    public List<Profile> getProfile(@PathVariable String username,
                                    @PathVariable String profileType) {
        return service.findByProfileType(username, ProfileType.valueOf(profileType.toUpperCase()));
    }
}
