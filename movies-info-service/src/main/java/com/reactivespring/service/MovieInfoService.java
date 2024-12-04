package com.reactivespring.service;


import com.reactivespring.domain.MovieInfo;
import com.reactivespring.repository.MovieInfoRespository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovieInfoService {

    private MovieInfoRespository movieInfoRespository;

    public MovieInfoService(MovieInfoRespository movieInfoRespository) {
        this.movieInfoRespository = movieInfoRespository;
    }

    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {

        return movieInfoRespository.save(movieInfo);
    }

    public Flux<MovieInfo> getAllMovieInfo() {
        return movieInfoRespository.findAll();
    }

    public Mono<MovieInfo> getMovieInfoById(String id) {
        return movieInfoRespository.findById(id);
    }

    public Mono<MovieInfo> updateMovieInfo(String id, MovieInfo updatedMovieInfo) {
       return movieInfoRespository.findById(id)
                .flatMap(movieInfo -> {
                    movieInfo.setCast(updatedMovieInfo.getCast());
                    movieInfo.setName(updatedMovieInfo.getName());
                    movieInfo.setRelease_date(updatedMovieInfo.getRelease_date());
                    movieInfo.setYear(updatedMovieInfo.getYear());
                    return movieInfoRespository.save(movieInfo);
                });

    }

    public Mono<Void> deleteMovieInfo(String id) {
        return movieInfoRespository.deleteById(id);
    }
}
