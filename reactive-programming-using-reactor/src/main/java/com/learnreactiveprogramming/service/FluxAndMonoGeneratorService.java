package com.learnreactiveprogramming.service;

import lombok.var;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

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

    //Flux FlatMap
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
     //  var delay =  new Random().nextInt(1000);
        var delay = 1000;
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

    //Mono FlatMap
    public Mono<List<String>> namesMono_flatMap(int stringLength){
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s-> s.length() > stringLength)
                .flatMap(this::splitStringsMono)
                .log(); //Mono<List of A,L,E,X>
    }

    private Mono<List<String>> splitStringsMono(String s) {
        var charArray = s.split("");
        List<String> charList = Arrays.asList(charArray);
        return Mono.just(charList);
    }

    //FaltMapMany - when we have operation than accept mono and return flux that time we use flatMapMany.
    public Flux<String> namesMono_flatMapMany(int stringLength){
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s-> s.length() > stringLength)
                .flatMapMany(this::splitStrings)
                .log(); //Mono<List of A,L,E,X>
    }

    ///////*********************************************************
    //Transfrom
    public Flux<String> namesFlux_Transfrom(int stringLength)
    {
        Function<Flux<String>, Flux<String>> filterMap =
                name -> name.map(String::toUpperCase)
                        .filter(s -> s.length() > stringLength);

        return  Flux.fromIterable(Arrays.asList("alex","ben","chloe"))
                .transform(filterMap)
                //ALEX, CHLOE -> A,L,E,X,C,H,O,L,E
                .flatMap(s -> splitStrings(s))
                .defaultIfEmpty("default")
                .log();


    }
    public Flux<String> namesFlux_Transfrom_switchIfEmpty(int stringLength)
    {
        Function<Flux<String>, Flux<String>> filterMap =
                name -> name.map(String::toUpperCase)
                        .filter(s -> s.length() > stringLength)
                        .flatMap(s -> splitStrings(s));

        //create a publisher for switching if data is null
        Flux<String> aDefault = Flux.just("default")
                .transform(filterMap);

        return  Flux.fromIterable(Arrays.asList("alex","ben","chloe"))
                .transform(filterMap)
                .switchIfEmpty(aDefault)
                .log();


    }


    ////Concat and concatwith
    public Flux<String> namesConcact(){
        Flux<String> abcFlux = Flux.just("A", "B", "C");
        Flux<String> defFlux = Flux.just("D", "E", "F");

        return Flux.concat(abcFlux,defFlux)
                .log();
    }

    public Flux<String> namesConcactWith(){
        Flux<String> abcFlux = Flux.just("A", "B", "C");
        //Flux<String> defFlux = Flux.just("D", "E", "F");
        Mono<String> dMono = Mono.just("D");

        return abcFlux.concatWith(dMono)
                .log();
    }

    public Flux<String> Explore_merge(){
        Flux<String> abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        Flux<String> defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));

        return Flux.merge(abcFlux,defFlux)
                .log();
    }

    public Flux<String> Explore_mergeWith(){
        Flux<String> abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        Mono<String> dMono = Mono.just("D");

        return abcFlux.mergeWith(dMono)
                .log();
    }


    public Flux<String> Explore_mergeSequential(){
        Flux<String> abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        Flux<String> defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));

        return Flux.mergeSequential(abcFlux,defFlux)
                .log();
    }

    public Flux<String> Explore_zip(){
        Flux<String> abcFlux = Flux.just("A", "B", "C");

        Flux<String> defFlux = Flux.just("D", "E","F");


        return Flux.zip(abcFlux,defFlux,(f,s)-> f + "|" + s)//AD,BE,CF
                .log();
    }

    public Flux<String> Explore_zip1(){
        Flux<String> abcFlux = Flux.just("A", "B","C");

        Flux<String> defFlux = Flux.just("D", "E","F");
        Flux<String> _123Flux = Flux.just("1", "2","3");

        Flux<String> _456Flux = Flux.just("4", "5","6");


        return Flux.zip(abcFlux,defFlux,_123Flux,_456Flux)
                .map(t4-> t4.getT1() + t4.getT2() +t4.getT3() + t4.getT4())//AD,BE,CF
                .log();
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
