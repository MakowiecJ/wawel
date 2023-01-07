package com.mako.wawel;

import com.mako.wawel.common.City;
import com.mako.wawel.common.MovieSoundType;
import com.mako.wawel.common.MovieType;
import com.mako.wawel.common.ScreenName;
import com.mako.wawel.request.EditRepertoireRequest;
import com.mako.wawel.request.EditScreeningRequest;
import com.mako.wawel.service.MoviesService;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer {

//    @Autowired
//    MoviesService service;
//
//    @Transactional
//    @PostConstruct
//    public Void initialize() {
//
//        EditScreeningRequest screening = EditScreeningRequest.builder()
//                .screenName(ScreenName.SALA1)
//                .movieId(1L)
//                .startTime(LocalTime.of(8, 0))
//                .movieType(MovieType.D2)
//                .movieSoundType(MovieSoundType.DUBBING)
//                .build();
//
//        EditRepertoireRequest editRepertoireRequest = EditRepertoireRequest.builder()
//                .city(City.KRAKOW)
//                .date(LocalDate.of(2023, 03, 01))
//                .screenings(List.of(screening))
//                .build();
//
//        service.editRepertoire(editRepertoireRequest);
//
//        return null;
//    }
}
