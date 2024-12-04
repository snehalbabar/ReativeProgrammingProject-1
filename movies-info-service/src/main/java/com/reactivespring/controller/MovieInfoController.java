package com.reactivespring.controller;


import com.reactivespring.domain.MovieInfo;
import com.reactivespring.service.MovieInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class MovieInfoController {

   private MovieInfoService movieInfoService;

    public MovieInfoController(MovieInfoService movieInfoService) {
        this.movieInfoService = movieInfoService;
    }


    @GetMapping("/moviesinfos")
    public Flux<MovieInfo> getAllMovieInfo(){

        return movieInfoService.getAllMovieInfo().log();
    }

    @GetMapping("/moviesinfos/{id}")
    public Mono<MovieInfo> getMovieInfo(@PathVariable String id){
        return movieInfoService.getMovieInfoById(id).log();
    }


    @PostMapping("/moviesinfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody MovieInfo movieInfo){
      return  movieInfoService.addMovieInfo(movieInfo).log();

    }

    @PutMapping("/moviesinfos/{id}")
    public Mono<MovieInfo> updateMovieInfo(@PathVariable String id, @RequestBody MovieInfo updatedMovieInfo){
        return movieInfoService.updateMovieInfo(id, updatedMovieInfo).log();

    }

    @DeleteMapping("/moviesinfos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfo(@PathVariable String id){
        return movieInfoService.deleteMovieInfo(id).log();

    }
}
