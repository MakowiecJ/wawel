package com.mako.wawel.persistence.repositories;

import com.mako.wawel.entity.cinema.Cinema;
import com.mako.wawel.entity.movies.Repertoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RepertoireRepository extends JpaRepository<Repertoire, Long> {
    Optional<Repertoire> findByCinemaAndDate(final Cinema cinema, final LocalDate date);
}
