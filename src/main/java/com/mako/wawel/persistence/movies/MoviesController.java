package com.mako.wawel.persistence.movies;

import com.mako.wawel.entity.movies.Movie;
import com.mako.wawel.request.movies.*;
import com.mako.wawel.response.movies.GeneralMovieResponse;
import com.mako.wawel.response.movies.GetRepertoireResponse;
import com.mako.wawel.response.movies.GetScreeningResponse;
import com.mako.wawel.response.movies.MovieReviewResponse;
import com.mako.wawel.service.movies.MoviesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/movies")
public class MoviesController {

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
    public void deleteMovie(@PathVariable final Long movieId) {
        service.deleteMovie(movieId);
    }

    @PostMapping("/reviews")
//    @Secured("role_user")
    public Void addReview(@RequestBody final AddReviewRequest request) {
        return service.addReview(request);
    }

    @GetMapping("/repertoire")
    public GetRepertoireResponse getRepertoire(@RequestBody final GetRepertoireRequest request) {
        return service.getRepertoire(request);
    }

    @PostMapping("/repertoire")
    public Void addRepertoire(@RequestBody final AddRepertoireRequest request) {
        return service.addRepertoire(request);
    }

    @GetMapping("/screening/{screeningId}")
    public GetScreeningResponse getScreening(@PathVariable final Long screeningId) {
        return service.getScreening(screeningId);
    }

    @PostMapping("/screening")
    public Void addScreening(@RequestBody final AddScreeningRequest request) {
        return service.addScreening(request);
    }
}
