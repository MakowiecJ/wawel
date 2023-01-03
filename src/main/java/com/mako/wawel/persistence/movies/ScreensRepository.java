package com.mako.wawel.persistence.movies;

import com.mako.wawel.common.ScreenName;
import com.mako.wawel.entity.movies.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreensRepository extends JpaRepository<Screen, Long> {
    Screen findByCinemaIdAndScreenName(final Long cinemaId, final ScreenName screenName);
}
