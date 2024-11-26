package com.learnreactiveprogramming.service;

import lombok.var;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService =
            new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        //given

        //when
        var nameFlux = fluxAndMonoGeneratorService.namesFlux();

        //then
        StepVerifier.create(nameFlux)
                //.expectNext("alex","ben","chole")
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void namesFluxMap() {
        //given
        int strLength = 3;

        //when
        var nameFlux = fluxAndMonoGeneratorService.namesFluxMap(strLength);

        //then
        StepVerifier.create(nameFlux)
                .expectNext("4-ALEX","5-CHLOE")
                .verifyComplete();

    }

    @Test
    void namesFlux_immutability() {
        //when
        var nameFlux = fluxAndMonoGeneratorService.namesFlux_immutability();

        //then
        StepVerifier.create(nameFlux)
                .expectNext("alex","ben","chloe")
                .verifyComplete();



    }

    @Test
    void namesFluxFlatmap() {
        //given
        int strLength = 3;

        //when
        var nameFlux = fluxAndMonoGeneratorService.namesFluxFlatmap(strLength);

        //then
        StepVerifier.create(nameFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();

    }

    @Test
    void namesFluxFlatmapAsync() {
        //given
        int strLength = 3;

        //when
        var nameFlux = fluxAndMonoGeneratorService.namesFluxFlatmapAsync(strLength);

        //then
        StepVerifier.create(nameFlux)
                //.expectNext("A","L","E","X","C","H","L","O","E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFluxConcatmapAsync() {
        //given
        int strLength = 3;

        //when
        var nameFlux = fluxAndMonoGeneratorService.namesFluxConcatmapAsync(strLength);

        //then
        StepVerifier.create(nameFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                //.expectNextCount(9)
                .verifyComplete();
    }
}