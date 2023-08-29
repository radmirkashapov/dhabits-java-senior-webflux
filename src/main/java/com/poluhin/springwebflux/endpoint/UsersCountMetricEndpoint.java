package com.poluhin.springwebflux.endpoint;

import com.poluhin.springwebflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Endpoint(id = "users.count")
@RequiredArgsConstructor
public class UsersCountMetricEndpoint {

    private final UserRepository userRepository;

    @ReadOperation
    public Mono<Map<String, Long>> count() {
        return userRepository
                .count()
                .map(count -> Map.of("count", count));
    }

}
