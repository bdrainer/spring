package com.bwg.app.model.account;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder(toBuilder = true)
public class Account {

    String username;

    AccountType type;

    AccountStatus status;
}
