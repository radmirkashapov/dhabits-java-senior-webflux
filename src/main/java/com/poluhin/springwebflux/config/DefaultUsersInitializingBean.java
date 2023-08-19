package com.poluhin.springwebflux.config;

import com.poluhin.springwebflux.domain.entity.UserEntity;
import com.poluhin.springwebflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DefaultUsersInitializingBean implements InitializingBean {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Create a default users...");
        List<UserEntity> userEntityList = List.of(
                new UserEntity("userId", "user", passwordEncoder.encode("password"), LocalDate.of(2020, 10, 12), "USER"),
                new UserEntity("adminId", "admin", passwordEncoder.encode("password"), LocalDate.of(2020, 10, 12), "ADMIN")
        );

        repository.saveAll(userEntityList).blockLast();
        log.info("Finish create a default users");
    }

}
