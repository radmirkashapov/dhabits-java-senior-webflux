package com.poluhin.springwebflux.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.logging.LogbackMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogbackMetricsConfig {

    @Bean
    public LogbackMetrics logbackMetrics(MeterRegistry meterRegistry) {
        var metrics = new LogbackMetrics();

        metrics.bindTo(meterRegistry);

        return metrics;
    }
}
