package com.bwg.app.repository.dynamo.entity;

import com.bwg.app.model.account.Account;
import com.bwg.app.model.profile.Profile;

public class EntityMapper {

    public static AccountEntity toAccountEntity(Account account) {
        return AccountEntity.builder()
                .pk(new CompositeKey(AccountEntity.PK_PREFIX, account.getUsername()))
                .sk(new CompositeKey(AccountEntity.SK_PREFIX, account.getUsername()))
                .account(account)
                .build();
    }

    public static ProfileEntity toProfileEntity(String username, Profile model) {
        return ProfileEntity.builder()
                .pk(new CompositeKey(ProfileEntity.PK_PREFIX, username))
                .sk(new CompositeKey(ProfileEntity.SK_PREFIX, model.type().toString(), model.getUserkey()))
                .profile(model)
                .build();
    }
}
