package com.mako.wawel.persistence.movies;

import com.mako.wawel.entity.movies.MovieEntity;
import com.mako.wawel.request.movies.AddMovieRequest;
import com.mako.wawel.service.movies.MoviesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    private MoviesService service;

    @GetMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<MovieEntity> getMovies() {
        return service.getMovies();
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public MovieEntity addMovie(@RequestBody final AddMovieRequest request) {
        return service.addMovie(request);
    }

    @DeleteMapping
    @Secured("ROLE_ADMIN")
    public void deleteMovie(@RequestParam final String movieId) {
        service.deleteMovie(movieId);
    }
}
