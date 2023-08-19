package com.poluhin.springwebflux.domain.model;

public record Tokens(
        Token access,
        Token refresh
) {
}
