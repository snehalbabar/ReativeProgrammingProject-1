package com.reactivespring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@RestController
public class FluxAndMonoController {

    @GetMapping("/flux")
    public Flux<Integer> flux(){
        return Flux.just(1,2,3).log();
    }
    @GetMapping("/mono")
    public Mono<String> mono(){
        return Mono.just("Hello World").log();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> stream(){
        return Flux.interval(Duration.ofSeconds(2)).log();
    }
}
