package com.poluhin.springwebflux.domain.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

@Document(value = "user")
public record UserEntity(
        @MongoId
        String id,
        String username,
        String password,

        LocalDate birth,
        String role
) {
}
