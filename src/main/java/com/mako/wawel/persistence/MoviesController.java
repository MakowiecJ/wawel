package com.mako.wawel.persistence;

import com.mako.wawel.entity.MovieEntity;
import com.mako.wawel.request.AddMovieRequest;
import com.mako.wawel.service.MoviesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    private MoviesService service;

    @GetMapping
    public List<MovieEntity> getMovies() {
        return service.getMovies();
    }

    @PostMapping
    public MovieEntity addMovie(@RequestBody final AddMovieRequest request) {
        return service.addMovie(request);
    }
}
