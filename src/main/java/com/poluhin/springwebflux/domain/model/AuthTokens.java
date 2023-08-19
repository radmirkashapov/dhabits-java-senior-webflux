package com.poluhin.springwebflux.domain.model;

public record AuthTokens(
        String accessToken,
        String refreshToken
) {
}
