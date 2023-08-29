package com.poluhin.springwebflux.filter;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static com.poluhin.springwebflux.config.MetricConsts.EXCEPTION;

@Component
@RequiredArgsConstructor
public class CustomExceptionFilter implements WebFilter {

    private final MeterRegistry meterRegistry;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).doOnError(ex -> {
            meterRegistry.counter(EXCEPTION, Tags.of("type", ex.getClass().getName())).increment();
        });
    }
}
