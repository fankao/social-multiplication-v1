package spring.microservices.multiplication.service;

import spring.microservices.multiplication.domain.Multiplication;
import spring.microservices.multiplication.domain.MultiplicationResultAttempt;

public interface MultiplicationService {
    /**
     * Creates a Multiplication object with two randomlygenerated factors
     * between 11 and 99.
     * *
     *
     * @return a Multiplication object with random factors
     */
    Multiplication createRandomMultiplication();

    /**
     * @return true if the attempt matches the result of the
     * multiplication, false otherwise.
     */
    boolean checkAttempt(final MultiplicationResultAttempt
                                 resultAttempt);
}
