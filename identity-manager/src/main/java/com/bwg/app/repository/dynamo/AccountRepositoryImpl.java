package com.bwg.app.repository.dynamo;

import com.bwg.app.model.account.Account;
import com.bwg.app.repository.AccountRepository;
import com.bwg.app.repository.dynamo.entity.AccountEntity;
import com.bwg.app.repository.dynamo.entity.EntityMapper;
import com.bwg.app.repository.dynamo.repository.AccountDynamoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountDynamoRepository repository;

    @Override
    public Account insert(Account account) {
        AccountEntity entity = repository.insert(EntityMapper.toAccountEntity(account));
        return entity.getAccount();
    }
    @Override
    public Account update(Account account) {
        AccountEntity entity = repository.update(EntityMapper.toAccountEntity(account));
        return entity.getAccount();
    }

    @Override
    public Optional<Account> findById(String identifier) {
        return repository.findById(identifier, identifier).map(AccountEntity::getAccount);
    }
}
