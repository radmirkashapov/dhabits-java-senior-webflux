package com.poluhin.springwebflux.weather.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

// обычно подобные интеграции пишу в отдельных модулях
// например integrations/vk, integrations/telegram и тд
@Configuration
public class WeatherClientConfiguration {

    @Bean
    WebClient openMeteoWebClient() {
        return WebClient
                .builder()
                .baseUrl("https://archive-api.open-meteo.com")
                .build();
    }

}
