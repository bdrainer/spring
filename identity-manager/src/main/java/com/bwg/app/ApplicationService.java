package com.bwg.app;

import com.bwg.app.common.exception.ResourceNotFoundException;
import com.bwg.app.model.account.Account;
import com.bwg.app.model.account.AccountStatus;
import com.bwg.app.model.account.AccountType;
import com.bwg.app.model.profile.Profile;
import com.bwg.app.model.profile.ProfileType;
import com.bwg.app.repository.AccountRepository;
import com.bwg.app.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final AccountRepository accountRepository;

    private final ProfileRepository profileRepository;

    public Account createAccount(String username) {
        log.info("Creating new account {}", username);
        return accountRepository.insert(Account.builder()
                .username(username)
                .type(AccountType.STANDARD)
                .status(AccountStatus.UNLOCKED)
                .build());
    }
    public Account updateAccount(String username, Account account) {
        log.info("Updating account {}", username);
        return accountRepository.update(account.toBuilder().username(username).build());
    }

    public Account findAccount(String username) {
        log.info("Finding account {}", username);
        return accountRepository.findById(username).orElseThrow(() ->
            new ResourceNotFoundException("Account does not exist")
        );
    }

    // ------------------------------------------------------------------------

    public Profile createProfile(String username, Profile profile) {
        log.info("Creating profile {}", username);
        profile.userKeyToUpper();
        findAccount(username);
        return profileRepository.insert(username, profile);
    }

    public Profile updateProfile(String username, Profile profile) {
        log.info("Creating profile {}", username);
        profile.userKeyToUpper();
        findAccount(username);
        return profileRepository.update(username, profile);
    }

    public Profile findProfile(String username, ProfileType type, String userkey) {
        log.info("Finding profile {}", username);
        return profileRepository.findById(username, type, userkey).orElseThrow(() ->
                new ResourceNotFoundException("Profile not found"));
    }

    public List<Profile> findByProfileType(String username, ProfileType profileType) {
        return profileRepository.findByProfileType(username, profileType);
    }
}
