package com.poluhin.springwebflux.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Hourly(
        @JsonProperty("time")
        List<LocalDateTime> time,

        @JsonProperty("temperature_2m")
        List<Double> temperature2m

) {
}
