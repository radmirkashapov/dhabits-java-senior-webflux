package com.poluhin.springwebflux.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResponse(
        Double latitude,
        Double longitude,

        Hourly hourly

) {
}
