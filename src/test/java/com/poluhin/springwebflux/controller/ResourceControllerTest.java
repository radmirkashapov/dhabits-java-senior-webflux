package com.poluhin.springwebflux.controller;

import com.poluhin.springwebflux.domain.model.ResourceObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class ResourceControllerTest extends AbstractTest {

    @Test
    @DisplayName("createResourceObject")
    void createResourceObject() throws Exception {
        // language=JSON5
        String requestBody = """
                {
                    "id": 1,
                    "value": "value1",
                    "path": "path1"
                }
                """;

        client
                .post()
                .uri("/resource")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("getResourceObject existingOk")
    void getResourceObject_existingOk() throws Exception {
        // language=JSON5
        String requestBody = """
                {
                    "id": 1,
                    "value": "value1",
                    "path": "path1"
                }
                """;

        client
                .post()
                .uri("/resource")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isOk();

        client
                .get()
                .uri("/resource/1")
                .exchange()
                .expectBody(ResourceObject.class)
                .isEqualTo(new ResourceObject(
                        1,
                        "value1",
                        "path1"
                ));

    }

    @Test
    @DisplayName("getResourceObject throwsNotFound")
    void getResourceObject_throwsNotFound() throws Exception {
        client
                .get()
                .uri("/resource/20000")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .isEmpty();

    }

    @Test
    @DisplayName("searchResourcesByValue success")
    void searchResourcesByValue_Success() throws Exception {
        client
                .get()
                .uri("/resource/search?q=vvfvfvfv")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(ResourceObject.class);
    }

    @Test
    @DisplayName("searchResourcesByPath success")
    void searchResourcesByPath() throws Exception {
        client
                .get().uri("/resource/searchByPath?path=path1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(ResourceObject.class);
    }

}
