package com.poluhin.springwebflux.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Random;

@Tag(name = "Exception test")
@RestController
@RequestMapping("/exception-test")
public class ExceptionTestController {

    @GetMapping
    public Mono<Object> test() {
        Random random = new Random();

        if (random.nextBoolean()) {
            return Mono.error(new IllegalArgumentException("True ex"));
        } else return Mono.error(new IllegalStateException("False ex"));
    }


}
