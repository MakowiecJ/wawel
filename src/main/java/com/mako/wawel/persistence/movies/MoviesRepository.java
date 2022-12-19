package com.mako.wawel.persistence.movies;

import com.mako.wawel.entity.movies.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends JpaRepository<MovieEntity, String> {
}
