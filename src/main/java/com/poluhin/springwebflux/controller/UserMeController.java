package com.poluhin.springwebflux.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/user/me")
@PreAuthorize("hasRole(\"USER\")")
public class UserMeController {

    @GetMapping
    public Mono<Map<String, Object>> current(@AuthenticationPrincipal Mono<Principal> principal) {
        return principal
                .map(user ->
                        Map.of(
                                "name", user.getName(),
                                "roles", AuthorityUtils.authorityListToSet(((Authentication) user)
                                        .getAuthorities())
                        )
                );
    }

}
