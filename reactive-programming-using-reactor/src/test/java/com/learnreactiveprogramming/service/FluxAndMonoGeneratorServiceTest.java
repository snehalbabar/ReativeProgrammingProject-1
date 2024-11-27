package com.learnreactiveprogramming.service;

import lombok.var;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.Arrays;

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

    @Test
    void namesMono_flatMap() {

        //given
        int strLength = 3;

        //when
        var nameFlux = fluxAndMonoGeneratorService.namesMono_flatMap(strLength);

        //then
        StepVerifier.create(nameFlux)
                .expectNext(Arrays.asList("A","L","E","X"))
                //.expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesMono_flatMapMany() {
        //given
        int strLength = 3;

        //when
        var nameFlux = fluxAndMonoGeneratorService.namesMono_flatMapMany(strLength);

        //then
        StepVerifier.create(nameFlux)
                .expectNext("A","L","E","X")
                //.expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFlux_Transfrom() {

        //given
        int strLength = 3;

        //when
        var nameFlux = fluxAndMonoGeneratorService.namesFlux_Transfrom(strLength);

        //then
        StepVerifier.create(nameFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();

    }

    @Test
    void namesFlux_Transfrom_defaultIfEmpty() {

        //given
        int strLength = 6;

        //when
        var nameFlux = fluxAndMonoGeneratorService.namesFlux_Transfrom(strLength);

        //then
        StepVerifier.create(nameFlux)
                .expectNext("default")
                .verifyComplete();

    }

    @Test
    void namesFlux_Transfrom_switchIfEmpty() {
        //given
        int strLength = 6;

        //when
        var nameFlux = fluxAndMonoGeneratorService.namesFlux_Transfrom_switchIfEmpty(strLength);

        //then
        StepVerifier.create(nameFlux)
                .expectNext("D","E","F","A","U","L","T")
                .verifyComplete();
    }

    @Test
    void namesConcact() {
        //when
        var nameFlux = fluxAndMonoGeneratorService.namesConcact();

        //then
        StepVerifier.create(nameFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void namesConcactWith() {
        //when
        var nameFlux = fluxAndMonoGeneratorService.namesConcactWith();

        //then
        StepVerifier.create(nameFlux)
                .expectNext("A","B","C","D")
                .verifyComplete();
    }

    @Test
    void explore_merge() {
        //when
        var nameFlux = fluxAndMonoGeneratorService.Explore_merge();

        //then
        StepVerifier.create(nameFlux)
                .expectNext("A","D","B","E","C","F")
                .verifyComplete();
    }

    @Test
    void explore_mergeWith() {
        //when
        var nameFlux = fluxAndMonoGeneratorService.Explore_mergeWith();

        //then
        StepVerifier.create(nameFlux)
                .expectNext("D","A","B","C")
                .verifyComplete();
    }

    @Test
    void explore_mergeSequential() {
        //when
        var nameFlux = fluxAndMonoGeneratorService.Explore_mergeSequential();

        //then
        StepVerifier.create(nameFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void explore_zip() {
        //when
        var nameFlux = fluxAndMonoGeneratorService.Explore_zip();

        //then
        StepVerifier.create(nameFlux)
                .expectNext("A|D","B|E","C|F")
                .verifyComplete();
    }

    @Test
    void explore_zip1() {
        //when
        var nameFlux = fluxAndMonoGeneratorService.Explore_zip1();

        //then
        StepVerifier.create(nameFlux)
                .expectNext("AD14","BE25","CF36")
                .verifyComplete();
    }
}