package com.poluhin.springwebflux.controller;

import com.poluhin.springwebflux.domain.model.AuthRequest;
import com.poluhin.springwebflux.domain.model.AuthTokens;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "Auth")
@RestController
@RequestMapping
public class AuthController {

    @PostMapping("/login")
    public Mono<AuthTokens> login(@Valid @RequestBody AuthRequest credentials) {
        throw new IllegalStateException("Add Spring Security to handle authentication");
    }


}
