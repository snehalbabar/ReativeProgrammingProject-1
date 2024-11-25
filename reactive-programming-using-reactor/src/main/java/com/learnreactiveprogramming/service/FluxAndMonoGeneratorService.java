package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux()
    {
      return  Flux.fromIterable(Arrays.asList("alex","ben","chole"))
              .log();
    }

    public Mono<String> nameMono()
    {
        return Mono.just("snehal").log();
    }
    public static void main(String[] args) {

        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();


        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(name ->
                        System.out.println(name));

        fluxAndMonoGeneratorService.nameMono()
                .subscribe(name ->
                        System.out.println("Mono Name :" + name));
    }


}
