package com.mako.wawel.persistence.repositories;

import com.mako.wawel.entity.movies.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningsRepository extends JpaRepository<Screening, Long> {
}
