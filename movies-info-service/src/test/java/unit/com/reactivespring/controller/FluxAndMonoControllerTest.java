package com.reactivespring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(controllers = FluxAndMonoController.class)
@AutoConfigureWebTestClient
class FluxAndMonoControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void flux() {
       webTestClient.get()
               .uri("/flux")
               .exchange()
               .expectStatus()
               .is2xxSuccessful()
               .expectBodyList(Integer.class)
               .hasSize(3);
    }
    @Test
    void flux_approach2() {
        Flux<Integer> responseBody = webTestClient.get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectNext(1,2,3)
                .verifyComplete();
    }

    @Test
    void flux_approach3() {
        webTestClient.get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .consumeWith(listEntityExchangeResult -> {
                 var resp =  listEntityExchangeResult.getResponseBody();
                 assert (Objects.requireNonNull(resp).size() == 3);
                });
    }

    @Test
    void Mono_approach1() {
        webTestClient.get()
                .uri("/mono")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .consumeWith( stringEntityExchangeResult -> {
                    var body = stringEntityExchangeResult.getResponseBody();
                    assertEquals("Hello World",body);
                });
    }

    @Test
    void stream_approach() {
        var responseBody = webTestClient.get()
                .uri("/stream")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectNext(0L,1L,2L)
                .thenCancel()
                .verify();
    }
}