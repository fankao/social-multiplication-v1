package spring.microservices.multiplication.service;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;
import spring.microservices.multiplication.domain.Multiplication;
import spring.microservices.multiplication.domain.MultiplicationResultAttempt;
import spring.microservices.multiplication.domain.User;
import spring.microservices.multiplication.repository.MultiplicationResultAttemptRepository;
import spring.microservices.multiplication.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class MultiplicationServiceTest {

    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Mock
    private MultiplicationResultAttemptRepository attemptRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService,attemptRepository,userRepository);
    }

    @Test
    void createRandomMultiplication() {
        //given (our mocked Random Generator service will
        //return first 50, then 30)
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

        //when
        Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

        // then
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
        //assertThat(multiplication.getResult()).isEqualTo(1500);
    }


    @Test
    void checkCorrectAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication
                (50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new
                MultiplicationResultAttempt(
                user, multiplication, 3000, false);
        MultiplicationResultAttempt verifiedAttempt = new
                MultiplicationResultAttempt(
                user, multiplication, 3000, true);
        given(userRepository.findByAlias("john_doe")).
                willReturn(Optional.empty());
        // when
        boolean attemptResult = multiplicationServiceImpl.
                checkAttempt(attempt);
        // then
        assertThat(attemptResult).isTrue();
        verify(attemptRepository).save(verifiedAttempt);

    }

    @Test
    void checkWrongAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication
                (50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new
                MultiplicationResultAttempt(
                user, multiplication, 3010, false);
        given(userRepository.findByAlias("john_doe")).
                willReturn(Optional.empty());
        // when
        boolean attemptResult = multiplicationServiceImpl.
                checkAttempt(attempt);
        // then
        assertThat(attemptResult).isFalse();
        verify(attemptRepository).save(attempt);
    }

    @Test
    boolean checkAttempt(final MultiplicationResultAttempt
                                 attempt) {
        // Checks if it's correct
        boolean correct = attempt.getResultAttempt() ==
                attempt.getMultiplication().
                        getFactorA() *
                        attempt.getMultiplication().
                                getFactorB();
        // Avoids 'hack' attempts
        Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct!!");
        // Creates a copy, now setting the 'correct' field accordingly
        MultiplicationResultAttempt checkedAttempt =
                new MultiplicationResultAttempt(attempt.getUser(),
                        attempt.getMultiplication(),
                        attempt.getResultAttempt(),
                        correct);
        // Returns the result
        return correct;
    }

    @Test
    public void retrieveStatsTest() {
// given
        Multiplication multiplication = new Multiplication
                (50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt1 = new
                MultiplicationResultAttempt(
                user, multiplication, 3010, false);
        MultiplicationResultAttempt attempt2 = new
                MultiplicationResultAttempt(
                user, multiplication, 3051, false);
        List<MultiplicationResultAttempt> latestAttempts =
                Lists.newArrayList(attempt1, attempt2);
        given(userRepository.findByAlias("john_doe")).
                willReturn(Optional.empty());
        given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("john_doe"))
                .willReturn(latestAttempts);
        // when
        List<MultiplicationResultAttempt> latestAttemptsResult =
                multiplicationServiceImpl.
                        getStatsForUser("john_doe");
        // then
        assertThat(latestAttemptsResult).isEqualTo
                (latestAttempts);
    }
}