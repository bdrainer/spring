package com.bwg.app.repository;

import com.bwg.app.model.account.Account;

import java.util.Optional;

/**
 * Stores account instances into a datastore.
 */
public interface AccountRepository {

    Account insert(Account account);

    Account update(Account account);

    Optional<Account> findById(String identifier);
}
