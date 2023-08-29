package com.poluhin.springwebflux.controller;

import com.poluhin.springwebflux.domain.model.AuthTokens;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;


public class UserMeControllerTest extends AbstractTest {


    @Test
    public void shouldReturnUserData() {

        // language=JSON5
        String requestBody = """
                {
                    "email": "user",
                    "password": "password"
                }
                """;

        var tokens = client
                .post()
                .uri("/login")
                .bodyValue(requestBody)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(AuthTokens.class)
                .getResponseBody()
                .next()
                .block();

        assert tokens != null;

        client
                .get()
                .uri("/user/me")
                .header(HttpHeaders.AUTHORIZATION, tokens.accessToken())
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void shouldThrow403() {
        client
                .get()
                .uri("/user/me")
                .exchange()
                .expectStatus()
                .isForbidden();
    }

}
