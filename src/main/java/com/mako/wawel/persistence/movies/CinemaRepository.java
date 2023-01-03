package com.mako.wawel.persistence.movies;

import com.mako.wawel.common.City;
import com.mako.wawel.entity.movies.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    Cinema findByCity(City city);
}
