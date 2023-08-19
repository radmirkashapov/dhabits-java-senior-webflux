package com.poluhin.springwebflux.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class BearerAuthorizationManager implements ReactiveAuthenticationManager {

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        log.trace(authentication.toString());
        return Mono.just(authentication);
    }

}
