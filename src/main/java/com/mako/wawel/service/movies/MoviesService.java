package com.mako.wawel.service.movies;

import com.mako.wawel.entity.movies.MovieEntity;
import com.mako.wawel.persistence.movies.MoviesRepository;
import com.mako.wawel.request.movies.AddMovieRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MoviesService {

    @Autowired
    private MoviesRepository repository;

    public List<MovieEntity> getMovies() {
        return repository.findAll();
    }

    public MovieEntity getMovie(final String movieId) {
        return repository.findById(movieId).orElseThrow();
    }

    public MovieEntity addMovie(final AddMovieRequest request) {
        return repository.save(MovieEntity.builder()
                .id(UUID.randomUUID().toString())
                .title(request.getTitle())
                .genre(request.getGenre())
                .minAge(request.getMinAge())
                .duration(request.getDuration())
                .description(request.getDescription())
                .build());
    }

    public void deleteMovie(final String movieId) {
        Optional<MovieEntity> toDelete = repository.findById(movieId);
        repository.delete(toDelete.orElseThrow());
    }
}
