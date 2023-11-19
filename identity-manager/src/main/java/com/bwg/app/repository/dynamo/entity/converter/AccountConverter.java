package com.bwg.app.repository.dynamo.entity.converter;

import com.bwg.app.model.account.Account;

public class AccountConverter extends JacksonAttributeConverter<Account>{
    public AccountConverter() {
        super(Account.class);
    }
}
