package spring.microservices.multiplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import spring.microservices.multiplication.domain.Multiplication;
import spring.microservices.multiplication.domain.MultiplicationResultAttempt;
import spring.microservices.multiplication.domain.User;
import spring.microservices.multiplication.service.MultiplicationService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static spring.microservices.multiplication.controller.MultiplicationResultAttemptController.*;
@WebMvcTest(MultiplicationResultAttemptController.class)
public class MultiplicationResultAttemptControllerTest {
    @Autowired
    private MultiplicationService multiplicationService;

    @Autowired
    private MockMvc mockMvc;

    // This object will be magically initialized by the
    //initFields method below.
    private JacksonTester<MultiplicationResultAttempt> jsonResult;
    private JacksonTester<ResultResponse> jsonResponse;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void postResultReturnCorrect() throws Exception {
        genericParameterizedTest(true);
    }

    public void postResultReturnNotCorrect() throws Exception {
        genericParameterizedTest(false);
    }

    private void genericParameterizedTest(boolean correct) throws Exception {
        // given (remember we're not testing here the service itself)
        given(multiplicationService
                .checkAttempt(any(MultiplicationResultAttempt.
                        class)))
                .willReturn(correct);
        User user = new User("john");
        Multiplication multiplication = new Multiplication(50, 70);
        MultiplicationResultAttempt attempt = new
                MultiplicationResultAttempt(
                user, multiplication, 3500);
        // when
        MockHttpServletResponse response = mockMvc.perform(
                post("/results").contentType(MediaType.
                        APPLICATION_JSON)
                        .content(jsonResult.write(attempt).
                                getJson()))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.
                OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResponse.write(new
                        ResultResponse(correct)).getJson());
    }
}