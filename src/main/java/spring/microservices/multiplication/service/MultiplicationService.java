package spring.microservices.multiplication.service;

import spring.microservices.multiplication.domain.Multiplication;

public interface MultiplicationService {
    /**
     * Creates a Multiplication object with two randomlygenerated factors
     * between 11 and 99.
     * *
     *
     * @return a Multiplication object with random factors
     */
    Multiplication createRandomMultiplication();
}
