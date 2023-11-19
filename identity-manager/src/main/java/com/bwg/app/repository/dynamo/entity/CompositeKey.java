package com.bwg.app.repository.dynamo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Creates a unique key in one of two formats.
 * <p>
 *     <ul>
 *         <li>Format 1:  "prefix#postfix"</li>
 *         <li>Format 2:  "prefix#postfix#userkey"</li>
 *     </ul>
 * <p>
 * This is a pattern used in Single Table Design for DynamoDB.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompositeKey implements Serializable {

    public static final String DELIMITER = "#";

    private String prefix;
    private String postfix;
    private String uniqueKey;

    public CompositeKey(String prefix, String postfix) {
        this.prefix = prefix;
        this.postfix = postfix;
    }

    @Override
    public String toString() {
        return Objects.isNull(uniqueKey)
                ? prefix + DELIMITER + postfix
                : prefix + DELIMITER + postfix + DELIMITER + uniqueKey;
    }

}
