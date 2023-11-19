package com.bwg.app;

import com.bwg.app.model.account.Account;
import com.bwg.app.model.account.AccountStatus;
import com.bwg.app.model.account.AccountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.bwg.app.repository.dynamo.repository.DynamoRepository.TABLE_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@Import({LocalStackConfiguration.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    private static final String USERNAME = "testuser";

    private static final Account NEW_ACCOUNT = Account.builder()
            .username(USERNAME)
            .type(AccountType.STANDARD)
            .status(AccountStatus.UNLOCKED)
            .build();

    private static final Account UPDATED_ACCOUNT = Account.builder()
            .username(USERNAME)
            .type(AccountType.PREMIUM)
            .status(AccountStatus.LOCKED)
            .build();

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DynamoDbClient client;

    private String accountsEndpoint;

    private String profilesEndpoint;

    @BeforeEach
    public void setUp() {
        // application-test.properties must have these user credentials;
        accountsEndpoint = "http://localhost:" + port + "/accounts/" + USERNAME;
        profilesEndpoint = accountsEndpoint + "/profiles";
        clearTable();
    }

    @Test
    void testCreateAccount() {
        // Create account
        ResponseEntity<Account> postResponse = createAccount();
        assertEquals(HttpStatus.CREATED.value(), postResponse.getStatusCode().value());
        assertAccountEquals(NEW_ACCOUNT, Objects.requireNonNull(postResponse.getBody()));

        // Get new account
        ResponseEntity<Account> getResponse = restTemplate.getForEntity(accountsEndpoint, Account.class);
        assertEquals(HttpStatus.OK.value(), getResponse.getStatusCode().value());
        assertAccountEquals(NEW_ACCOUNT, Objects.requireNonNull(getResponse.getBody()));
    }

    @Test
    void testUpdateAccount() {
        createAccount();

        // Update account
        ResponseEntity<Account> putResponse = updateAccount();
        assertEquals(HttpStatus.OK.value(), putResponse.getStatusCode().value());
        assertAccountEquals(UPDATED_ACCOUNT, Objects.requireNonNull(putResponse.getBody()));

        // Get updated account
        ResponseEntity<Account> getResponse = restTemplate.getForEntity(accountsEndpoint, Account.class);
        assertEquals(HttpStatus.OK.value(), getResponse.getStatusCode().value());
        assertAccountEquals(UPDATED_ACCOUNT, Objects.requireNonNull(getResponse.getBody()));
    }

    @Test
    void testAccountAlreadyExists() {
        // Create account
        createAccount();

        // attempt to create the same account
        ResponseEntity<String> response = restTemplate.exchange(
                accountsEndpoint, HttpMethod.POST, null, String.class);

        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode().value());
    }

    @Test
    void testAccountNotFound() {
        // attempt to update an account that doesn't exist
        ResponseEntity<String> response = restTemplate.exchange(accountsEndpoint, HttpMethod.PUT,
                new HttpEntity<>(UPDATED_ACCOUNT), String.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
    }

    // ------------------------------------------------------------------------

    private void assertAccountEquals(Account expected, Account actual) {
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

    private ResponseEntity<Account> createAccount() {
        return restTemplate.exchange(accountsEndpoint, HttpMethod.POST, null, Account.class);
    }

    private ResponseEntity<Account> updateAccount() {
        return restTemplate.exchange(accountsEndpoint, HttpMethod.PUT, new HttpEntity<>(UPDATED_ACCOUNT), Account.class);
    }

    private void clearTable() {
        ScanResponse response = client.scan(ScanRequest.builder().tableName(TABLE_NAME).build());
        List<Map<String, AttributeValue>> items = response.items();
        for (Map<String, AttributeValue> item : items) {
            Map<String, AttributeValue> key = new HashMap<>();
            key.put("pk", AttributeValue.builder().s(item.get("pk").s()).build());
            key.put("sk", AttributeValue.builder().s(item.get("sk").s()).build());
            client.deleteItem(DeleteItemRequest.builder().tableName(TABLE_NAME).key(key).build());
        }
    }
}
