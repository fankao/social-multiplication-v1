package spring.microservices.multiplication.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import spring.microservices.multiplication.domain.Multiplication;
import spring.microservices.multiplication.domain.MultiplicationResultAttempt;
import spring.microservices.multiplication.domain.User;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class MultiplicationServiceTest {
    @Autowired
    private MultiplicationService multiplicationService;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        multiplicationService = new MultiplicationServiceImpl(randomGeneratorService);
    }

    @Test
    void createRandomMultiplication() {
        //given (our mocked Random Generator service will
        //return first 50, then 30)
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

        //when
        Multiplication multiplication = multiplicationService.createRandomMultiplication();

        // then
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
        //assertThat(multiplication.getResult()).isEqualTo(1500);
    }


    @Test
    void checkCorrectAttemptTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("Minh Chien");
        MultiplicationResultAttempt resultAttempt =
                new MultiplicationResultAttempt(user, multiplication, 3000);

        //when
        boolean attemptResult = multiplicationService.checkAttempt(resultAttempt);

        //assert
        assertThat(attemptResult).isTrue();

    }

    @Test
    void checkWrongAttemptTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("Minh Chien");
        MultiplicationResultAttempt resultAttempt =
                new MultiplicationResultAttempt(user, multiplication, 3010);

        //when
        boolean attemptResult = multiplicationService.checkAttempt(resultAttempt);

        //assert
        assertThat(attemptResult).isFalse();
    }
}