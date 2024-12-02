package com.reactivespring.repository;

import com.reactivespring.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryIntegrationTest {

    @Autowired
    MovieInfoRespository movieInfoRespository;

    @BeforeEach
    void setUp() {
        var movieinfos = List.of(new MovieInfo(null, "Batman Begins",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight Rises",
                        2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));



        movieInfoRespository.saveAll(movieinfos)
                .blockLast();

    }

    @AfterEach
    void tearDown() {
        movieInfoRespository.deleteAll().block();
    }

    @Test
    void findAll() {

      var moviesInfoFlux =  movieInfoRespository.findAll().log();

      //then
        StepVerifier.create(moviesInfoFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById() {

        var moviesInfoMono =  movieInfoRespository.findById("abc").log();

        //then
        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfo -> {
                    assertEquals("Dark Knight Rises", movieInfo.getName());
                })
                .verifyComplete();
    }

    @Test
    void saveMovieInfo() {

       var movie = new MovieInfo(null, "Batman Begins1",
                2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

       var moviesInfoMono =  movieInfoRespository.save(movie).log();

        //then
        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfo -> {
                    assertNotNull(movie.getMovieInfoId());
                    assertEquals("Batman Begins1", movieInfo.getName());
                })
                .verifyComplete();
    }

    @Test
    void updateMovieInfo() {

        var exsitingMovie = movieInfoRespository.findById("abc").block();
       exsitingMovie.setYear(2024);

        var moviesInfoMono =  movieInfoRespository.save(exsitingMovie).log();

        //then
        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfo -> {

                    assertEquals(2024, movieInfo.getYear());
                })
                .verifyComplete();
    }

    @Test
    void deleteMovieInfo() {

          movieInfoRespository.deleteById("abc").block();
        var moviesInfoFlux = movieInfoRespository.findAll().log();


        //then
        StepVerifier.create(moviesInfoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}