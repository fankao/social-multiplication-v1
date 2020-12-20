package spring.microservices.multiplication.domain;

import lombok.*;

/**
 * Stores information to identify the user.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class User {
    private final String alias;

    protected User() {
        alias = null;
    }
}
