package com.poluhin.springwebflux.domain.model;

public record WeatherRequest(
        Double lat,
        Double lon
) {
}
