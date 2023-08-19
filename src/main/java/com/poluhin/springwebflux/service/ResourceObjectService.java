package com.poluhin.springwebflux.service;

import com.poluhin.springwebflux.domain.entity.ResourceObjectEntity;
import com.poluhin.springwebflux.domain.model.ResourceObject;
import com.poluhin.springwebflux.repository.ResourceObjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceObjectService {

    private final ResourceObjectRepository repository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Integer> save(ResourceObject resourceObject) {

        log.debug(String.format("save: %s", resourceObject));

        return repository.save(new ResourceObjectEntity(
                resourceObject.getId(), resourceObject.getValue(),
                resourceObject.getPath())).map(ResourceObjectEntity::getId);
    }

    public Mono<ResourceObject> get(int id) {

        log.debug(String.format("get by id: %s", id));

        return repository.findById(id)
                .map(r -> new ResourceObject(r.getId(), r.getValue(), r.getPath()));
    }

    public Flux<ResourceObject> searchByValue(String q) {

        log.debug(String.format("search by value: %s", q));

        return repository.findByValueLikeIgnoreCase(String.format("%%%s%%", q))
                .map(r -> new ResourceObject(r.getId(), r.getValue(), r.getPath()));
    }

    public Flux<ResourceObject> searchByPath(String path) {

        log.debug(String.format("search by path: %s", path));

        return repository.findByPath(path)
                .map(r -> new ResourceObject(r.getId(), r.getValue(), r.getPath()));
    }

}
