package com.bwg.app.repository.dynamo.repository;

import com.bwg.app.repository.dynamo.entity.CompositeKey;
import com.bwg.app.repository.dynamo.entity.ProfileEntity;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.Optional;

@Repository
public class ProfileDynamoRepository extends AbstractDynamoRepository<ProfileEntity> {

    private final TableSchema<ProfileEntity> tableSchema;

    public ProfileDynamoRepository(DynamoDbClient client, DynamoDbEnhancedClient enhancedClient) {
        super(client, enhancedClient);
        this.tableSchema = TableSchema.fromClass(ProfileEntity.class);
    }

    public Optional<ProfileEntity> findById(String username, String profileType, String userKey) {
        return Optional.ofNullable(queryById(toPkString(username), toSkString(profileType, userKey)));
    }

    public List<ProfileEntity> findByProfileType(String username, String profileType) {
        PageIterable<ProfileEntity> results = table().query(
                QueryConditional.sortBeginsWith(k ->
                        k.partitionValue(toPkString(username)).sortValue(toSkString(profileType))
                )
        );
        return results.items().stream().toList();
    }

    @Override
    DynamoDbTable<ProfileEntity> table() {
        return enhancedClient.table(TABLE_NAME, tableSchema);
    }

    @Override
    String toPkString(String identifier) {
        return new CompositeKey(ProfileEntity.PK_PREFIX, identifier).toString();
    }

    @Override
    String toSkString(String identifier) {
        return new CompositeKey(ProfileEntity.SK_PREFIX, identifier).toString();
    }
    String toSkString(String identifier, String userKey) {
        return new CompositeKey(ProfileEntity.SK_PREFIX, identifier, userKey).toString();
    }

    @Override
    void update(ProfileEntity source, ProfileEntity target) {
        target.setProfile(source.getProfile());
    }
}
