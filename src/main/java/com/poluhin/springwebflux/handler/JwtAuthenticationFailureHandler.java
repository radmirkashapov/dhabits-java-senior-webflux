package com.poluhin.springwebflux.handler;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.poluhin.springwebflux.config.MetricConsts.AUTH_FAILED;

@Component
public class JwtAuthenticationFailureHandler extends ServerAuthenticationEntryPointFailureHandler {

    private final Counter authFailedCounter = Metrics.counter(AUTH_FAILED);

    public JwtAuthenticationFailureHandler() {
        super(new HttpBasicServerAuthenticationEntryPoint());
    }

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        authFailedCounter.increment();
        return super.onAuthenticationFailure(webFilterExchange, exception);
    }
}
