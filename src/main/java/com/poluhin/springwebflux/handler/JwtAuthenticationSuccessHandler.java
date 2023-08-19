package com.poluhin.springwebflux.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poluhin.springwebflux.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    private final JwtService jwtService;


    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        log.debug("auth: " + authentication);

        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();

        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        try {
            var tokens = objectMapper.writeValueAsBytes(jwtService.generateTokens(
                    new User(authentication.getName(),
                            "empty",
                            authentication.getAuthorities())
            ));

            DataBuffer dataBuffer = response.bufferFactory().wrap(tokens);

            return response.writeWith(Mono.just(dataBuffer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
