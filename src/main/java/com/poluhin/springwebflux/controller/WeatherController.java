package com.poluhin.springwebflux.controller;

import com.poluhin.springwebflux.domain.model.WeatherRequest;
import com.poluhin.springwebflux.service.BirthdayWeatherService;
import com.poluhin.springwebflux.weather.model.WeatherResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "Weather (with SS)")
@RestController
@RequestMapping("/weather")
@AllArgsConstructor
@PreAuthorize("hasRole(\"ADMIN\")")
public class WeatherController {

    private final BirthdayWeatherService birthdayWeatherService;

    @GetMapping("/{userId}")
    public Mono<WeatherResponse> getWeatherByBirth(
            @ParameterObject WeatherRequest weatherRq,
            @PathVariable String userId
    ) {
        return birthdayWeatherService.getWeatherByUser(weatherRq.lat(), weatherRq.lon(), userId);
    }
}
