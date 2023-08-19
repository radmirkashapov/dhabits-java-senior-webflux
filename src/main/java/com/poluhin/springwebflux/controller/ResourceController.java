package com.poluhin.springwebflux.controller;

import com.poluhin.springwebflux.domain.model.ResourceObject;
import com.poluhin.springwebflux.service.ResourceObjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Resource (without SS)")
@RestController
@AllArgsConstructor
@RequestMapping("/resource")
public class ResourceController {

    private final ResourceObjectService service;

    @PostMapping
    public Mono<Integer> createResourceObject(@RequestBody ResourceObject object) {
        return service.save(object);
    }

    @GetMapping("/{id}")
    public Mono<ResourceObject> getResourceObject(@PathVariable Integer id) {
        return service.get(id);
    }

    @GetMapping("/search")
    public Flux<ResourceObject> searchByQuery(@RequestParam String q) {
        return service.searchByValue(q);
    }

    @GetMapping("/searchByPath")
    public Flux<ResourceObject> searchByPath(@RequestParam String path) {
        return service.searchByPath(path);
    }


}
