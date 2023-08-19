package com.poluhin.springwebflux.repository;

import com.poluhin.springwebflux.domain.entity.ResourceObjectEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ResourceObjectRepository extends ReactiveCrudRepository<ResourceObjectEntity, Integer> {
    Flux<ResourceObjectEntity> findByValueLikeIgnoreCase(String value);

    Flux<ResourceObjectEntity> findByPath(String path);
}
