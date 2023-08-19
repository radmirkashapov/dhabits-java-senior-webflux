package com.poluhin.springwebflux.service;

import com.poluhin.springwebflux.repository.UserRepository;
import com.poluhin.springwebflux.weather.client.ArchiveWeatherClient;
import com.poluhin.springwebflux.weather.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class BirthdayWeatherService {

    private final ArchiveWeatherClient archiveWeatherClient;
    private final UserRepository userRepository;


    public Mono<WeatherResponse> getWeatherByUser(Double latitude, Double longitude, String userId) {
        log.debug("getWeatherByBirthday");

        return userRepository
                .findById(userId)
                .flatMap(user -> archiveWeatherClient
                        .getArchiveWeather(latitude, longitude, user.birth(), user.birth().plusDays(1)))
                .onErrorResume(WebClientResponseException.class, (err) -> Mono.error(new IllegalArgumentException("error")));
    }

}
