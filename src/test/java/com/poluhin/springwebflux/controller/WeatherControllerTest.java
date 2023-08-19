package com.poluhin.springwebflux.controller;

import com.poluhin.springwebflux.weather.model.WeatherResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

public class WeatherControllerTest extends AbstractTest {

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void shouldReturnWeather() throws Exception {
        client
                .get()
                .uri("/weather/1?lat=50&lot=30")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(WeatherResponse.class);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void shouldThrow403() throws Exception {
        client
                .get()
                .uri("/weather/1?lat=50&lot=30")
                .exchange()
                .expectStatus()
                .isForbidden();
    }

    @Test
    public void shouldThrow401() throws Exception {
        client
                .get()
                .uri("/weather/1?lat=50&lot=30")
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

}
