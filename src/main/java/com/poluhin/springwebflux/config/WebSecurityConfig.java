package com.poluhin.springwebflux.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poluhin.springwebflux.converter.AuthRequestConverter;
import com.poluhin.springwebflux.converter.JwtAuthConverter;
import com.poluhin.springwebflux.handler.BearerAuthorizationManager;
import com.poluhin.springwebflux.handler.JwtAuthenticationSuccessHandler;
import com.poluhin.springwebflux.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    private final ReactiveUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    private final String[] swaggerPaths = new String[]{
            "/webjars/swagger-ui/index.html",
            "/webjars/swagger-ui/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/v3/api-docs.yaml/**"
    };

    @Bean
    public SecurityWebFilterChain apiFilterChain(ServerHttpSecurity http) {
        var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);

        ServerAuthenticationConverter authRequestConverter = new AuthRequestConverter();

        AuthenticationWebFilter authenticationFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationFilter.setServerAuthenticationConverter(authRequestConverter);
        authenticationFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler(objectMapper, jwtService));
        authenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/login"));

        AuthenticationWebFilter bearerAuthWebFilter = new AuthenticationWebFilter(new BearerAuthorizationManager());
        bearerAuthWebFilter.setServerAuthenticationConverter(new JwtAuthConverter(jwtService));

        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange((authorize) ->
                        authorize
                                .pathMatchers("/resource", "/resource/**").permitAll()
                                .pathMatchers(swaggerPaths).permitAll()
                                .anyExchange().authenticated())
                .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAt(bearerAuthWebFilter, SecurityWebFiltersOrder.AUTHORIZATION);

        return http.build();
    }

}
