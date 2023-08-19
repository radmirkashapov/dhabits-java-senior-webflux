package com.poluhin.springwebflux.config;

import com.poluhin.springwebflux.domain.model.Tokens;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String issuer,
        Tokens tokens
) {
}
