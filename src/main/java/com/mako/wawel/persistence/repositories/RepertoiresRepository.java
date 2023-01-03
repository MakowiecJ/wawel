package com.mako.wawel.persistence.repositories;

import com.mako.wawel.entity.cinema.Cinema;
import com.mako.wawel.entity.movies.Repertoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RepertoiresRepository extends JpaRepository<Repertoire, Long> {
    @Query("SELECT r FROM Repertoire r WHERE r.cinema = :cinema AND :date BETWEEN r.startDate AND r.endDate")
    Repertoire findByCinemaAndDate(@Param("cinema") Cinema cinema, @Param("date") LocalDate date);
}
