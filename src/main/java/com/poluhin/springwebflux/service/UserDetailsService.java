package com.poluhin.springwebflux.service;

import com.poluhin.springwebflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsService implements ReactiveUserDetailsService {

    private final UserRepository repository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        log.debug("username: " + username);
        return repository.findByUsername(username).map(userEntity ->
                User.withUsername(userEntity.username())
                        .password(userEntity.password())
                        .roles(userEntity.role()).build()
        );
    }

}
