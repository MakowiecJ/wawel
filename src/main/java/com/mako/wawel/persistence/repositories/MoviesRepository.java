package com.mako.wawel.persistence.repositories;

import com.mako.wawel.entity.movies.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends JpaRepository<Movie, Long> {
}
