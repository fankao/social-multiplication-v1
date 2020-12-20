package spring.microservices.multiplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.microservices.multiplication.domain.Multiplication;
import spring.microservices.multiplication.service.MultiplicationService;

@RestController
@RequestMapping("/multiplications")
public class MultiplicationController {
    private final MultiplicationService multiplicationService;

    public MultiplicationController(final MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

    @GetMapping("/random")
    Multiplication getRandomMultiplication() {
        return multiplicationService.createRandomMultiplication();
    }
}
