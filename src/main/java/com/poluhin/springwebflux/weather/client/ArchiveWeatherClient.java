package com.poluhin.springwebflux.weather.client;

import com.poluhin.springwebflux.weather.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArchiveWeatherClient {

    private final WebClient openMeteoWebClient;

    public Mono<WeatherResponse> getArchiveWeather(Double latitude, Double longitude, LocalDate from, LocalDate to) {
        return openMeteoWebClient
                .get()
                .uri(uri ->
                        uri
                                .path("/v1/archive")
                                .queryParam("latitude", latitude)
                                .queryParam("longitude", longitude)
                                .queryParam("start_date", from)
                                .queryParam("end_date", to)
                                .queryParam("hourly", "temperature_2m")
                                .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .log();

    }

}
