package com.learnreactiveprogramming.service;

import lombok.var;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

import static reactor.core.publisher.Flux.fromArray;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux()
    {
      return  Flux.fromIterable(Arrays.asList("alex","ben","chloe"));

    }

    public Flux<String> namesFluxMap(int stringLength)
    {
        //filter thhe string whose length is greater than 3
        return  Flux.fromIterable(Arrays.asList("alex","ben","chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .map(s -> s.length() + "-" + s)
                .log();

               // .map(i -> i.toUpperCase());
    }

    //FlatMap
    public Flux<String> namesFluxFlatmap(int stringLength)
    {
        //filter thhe string whose length is greater than 3
        return  Flux.fromIterable(Arrays.asList("alex","ben","chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                //ALEX, CHLOE -> A,L,E,X,C,H,O,L,E
               .flatMap(s -> splitStrings(s))
                .log();

        // .map(i -> i.toUpperCase());
    }

//FlatMap Async
    public Flux<String> namesFluxFlatmapAsync(int stringLength)
    {
        //filter thhe string whose length is greater than 3
        return  Flux.fromIterable(Arrays.asList("alex","ben","chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                //ALEX, CHLOE -> A,L,E,X,C,H,O,L,E
                .flatMap(s -> splitStringsWithDelay(s))
                .log();

        // .map(i -> i.toUpperCase());
    }

    //FlatMap Async
    public Flux<String> namesFluxConcatmapAsync(int stringLength)
    {
        //filter thhe string whose length is greater than 3
        return  Flux.fromIterable(Arrays.asList("alex","ben","chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                //ALEX, CHLOE -> A,L,E,X,C,H,O,L,E
                .concatMap(s -> splitStringsWithDelay(s))
                .log();

        // .map(i -> i.toUpperCase());
    }


    //ALEX -> Flux(A,L,E,X)
    public Flux<String> splitStrings(String s)
    {
       var chaArray =  s.split("");
       return Flux.fromArray(chaArray);
    }

    public Flux<String> splitStringsWithDelay(String s)
    {
       var chaArray =  s.split("");
       var delay =  new Random().nextInt(1000);
       return Flux.fromArray(chaArray)
                .delayElements(Duration.ofMillis(delay));
    }

    public Flux<String> namesFlux_immutability()
    {
        var nameFlux =  Flux.fromIterable(Arrays.asList("alex","ben","chole"));
        nameFlux.map(String::toUpperCase);
        return nameFlux;

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

        fluxAndMonoGeneratorService.namesFluxMap(2)
                .subscribe(n -> System.out.println("upper case "+ n));
    }


}
