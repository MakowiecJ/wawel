package com.mako.wawel.service;

import com.mako.wawel.entity.MovieEntity;
import com.mako.wawel.persistence.MoviesRepository;
import com.mako.wawel.request.AddMovieRequest;
import lombok.AllArgsConstructor;
import org.hibernate.id.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MoviesService {

    @Autowired
    private MoviesRepository repository;

    public List<MovieEntity> getMovies() {
        return repository.findAll();
    }

    public MovieEntity addMovie(AddMovieRequest request) {
        return repository.save(MovieEntity.builder()
                .id(UUID.randomUUID().toString())
                .title(request.getTitle())
                .genre(request.getGenre())
                .minAge(request.getMinAge())
                .duration(request.getDuration())
                .description(request.getDescription())
                .build());
    }
}
