package spring.microservices.multiplication.domain;

import lombok.*;

import java.util.UUID;

/**
 * Identifies the attempt from a {@link User} to solve a
 * {@link Multiplication}.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class MultiplicationResultAttempt {
    private final User user;
    private final Multiplication multiplication;
    private final int resultAttempt;
    // Empty constructor for JSON (de)serialization
    MultiplicationResultAttempt() {
        user = null;
        multiplication = null;
        resultAttempt = -1;
    }

}
