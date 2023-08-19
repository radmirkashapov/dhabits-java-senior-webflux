package com.poluhin.springwebflux.domain.model;

public record Token(
        Long ttlInSeconds,
        String key
) {
}
