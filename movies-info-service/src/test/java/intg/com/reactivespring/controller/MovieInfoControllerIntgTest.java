package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.repository.MovieInfoRespository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class MovieInfoControllerIntgTest {

    @Autowired
    MovieInfoRespository movieInfoRespository;

    @Autowired
    WebTestClient webTestClient;

    static String MOVIE_IFO_URL = "/v1/moviesinfos";
    static String SINGLE_MOVIE = "/v1/movieinfo";

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
    void addMovieInfo() {
        //given
        var movie = new MovieInfo(null, "Tangled",
                2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20"));


        webTestClient.post()
                .uri(MOVIE_IFO_URL)
                .bodyValue(movie)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                   var savedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
                   assert savedMovieInfo != null;
                   assert savedMovieInfo.getMovieInfoId() != null;
                });


    }

    @Test
    void getAllMovieInfo(){

        webTestClient
                .get()
                .uri(MOVIE_IFO_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(3);
    }

   @Test
   void getMovieInfoById(){

       //given
       var movieId ="abc";

       webTestClient
               .get()
               .uri(MOVIE_IFO_URL +"/{id}",movieId)
               .exchange()
               .expectStatus()
               .is2xxSuccessful()
               .expectBody()
               .jsonPath("$.name").isEqualTo("Dark Knight Rises");




//               .expectBody(MovieInfo.class)
//               .consumeWith(movieInfoEntityExchangeResult -> {
//
//                  var movieInfo = movieInfoEntityExchangeResult.getResponseBody();
//                  assertNotNull(movieInfo);
//               });

   }

    @Test
    void updateMovieInfo() {
        //given
        var movieId = "abc";
        var movie = new MovieInfo(null, "Tangled II",
                2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20"));


        webTestClient.put()
                .uri(MOVIE_IFO_URL+"/{id}",movieId)
                .bodyValue(movie)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    var updatedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assert updatedMovieInfo != null;
                    assert updatedMovieInfo.getMovieInfoId() != null;
                    assertEquals("Tangled II" , updatedMovieInfo.getName());
                });


    }

    @Test
    void deleteMovieInfoById(){

        //given
        var movieId ="abc";

        webTestClient
                .delete()
                .uri(MOVIE_IFO_URL +"/{id}",movieId)
                .exchange()
                .expectStatus()
                .isNoContent();


    }

}