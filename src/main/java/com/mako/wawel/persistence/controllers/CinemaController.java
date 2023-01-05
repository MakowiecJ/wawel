package com.mako.wawel.persistence.controllers;

import com.mako.wawel.common.City;
import com.mako.wawel.entity.movies.Movie;
import com.mako.wawel.request.*;
import com.mako.wawel.response.*;
import com.mako.wawel.service.MoviesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
@RequestMapping("/movies")
public class CinemaController {

    @Autowired
    private MoviesService service;

    @GetMapping
    public List<GeneralMovieResponse> getMovies() {
        return service.getMovies();
    }

    @GetMapping("/reviews/{movieId}")
    public List<MovieReviewResponse> getMovieReviews(@PathVariable final Long movieId) {
        return service.getMovieReviews(movieId);
    }

    @GetMapping("/{movieId}")
    public GeneralMovieResponse getMovie(@PathVariable final Long movieId) {
        return service.getMovie(movieId);
    }

    @PostMapping
//    @Secured("role_admin")
    public Movie addMovie(@RequestBody final AddMovieRequest request) {
        return service.addMovie(request);
    }

    @DeleteMapping("{movieId}")
//    @Secured("role_admin")
    public ResponseEntity<String> deleteMovie(@PathVariable final Long movieId) {
        return service.deleteMovie(movieId);
    }

    @PostMapping("/reviews")
//    @Secured("role_user")
    public ResponseEntity<String> addReview(@RequestBody final AddReviewRequest request) {
        return service.addReview(request);
    }

    @GetMapping("review/{reviewId}")
    public ResponseEntity<?> getReview(@PathVariable final Long reviewId) {
        return service.getReview(reviewId);
    }

    @GetMapping("/repertoire")
    public GetRepertoireResponse getRepertoire(
            @RequestParam final City city,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate date) {
        return service.getRepertoire(new GetRepertoireRequest(city, date));
    }

    @PostMapping("/repertoire")
    public ResponseEntity<String> addRepertoire(@RequestBody final AddRepertoireRequest request) {
        return service.addRepertoire(request);
    }

    @GetMapping("/screening/{screeningId}")
    public GetScreeningResponse getScreening(@PathVariable final Long screeningId) {
        return service.getScreening(screeningId);
    }

    @PostMapping("/screening")
    public ResponseEntity<String> addScreening(@RequestBody final AddScreeningRequest request) {
        return service.addScreening(request);
    }


    @GetMapping("/users/{userId}")
    public GetUserInfoResponse getUserInfo(@PathVariable final Long userId) {
        return service.getUserInfo(userId);
    }

    @PostMapping("/tickets/buy")
    public ResponseEntity<String> buyTickets(@RequestBody final BuyTicketsRequest request) {
        return service.buyTickets(request);
    }

    @PostMapping("/archive/{movieId}")
    public ResponseEntity<String> archiveMovie(@PathVariable final Long movieId) {
        service.archiveMovie(movieId);
        return null;
    }
}
