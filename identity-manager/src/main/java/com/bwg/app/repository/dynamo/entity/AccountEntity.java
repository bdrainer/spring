package com.bwg.app.repository.dynamo.entity;

import com.bwg.app.model.account.Account;
import com.bwg.app.repository.dynamo.entity.converter.AccountConverter;
import com.bwg.app.repository.dynamo.entity.converter.CompositeKeyConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Setter
@DynamoDbBean
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AccountEntity implements DynamoEntity<CompositeKey> {

    public static final String PK_PREFIX = "ACCOUNT";
    public static final String SK_PREFIX = "ACCOUNT";

    @Getter(onMethod_ = {@DynamoDbPartitionKey, @DynamoDbAttribute("pk"),
            @DynamoDbConvertedBy(CompositeKeyConverter.class)})
    protected CompositeKey pk; // partition key

    @Getter(onMethod_ = {@DynamoDbSortKey, @DynamoDbAttribute("sk"),
            @DynamoDbConvertedBy(CompositeKeyConverter.class)})
    protected CompositeKey sk; // sort key

    @Getter(onMethod_ = {@DynamoDbAttribute("document"), @DynamoDbConvertedBy(AccountConverter.class)})
    private Account account;
}
