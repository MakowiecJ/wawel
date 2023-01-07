package com.mako.wawel;

import com.mako.wawel.common.City;
import com.mako.wawel.common.MovieSoundType;
import com.mako.wawel.common.MovieType;
import com.mako.wawel.common.ScreenName;
import com.mako.wawel.request.EditRepertoireRequest;
import com.mako.wawel.request.EditScreeningRequest;
import com.mako.wawel.service.MoviesService;
import lombok.AllArgsConstructor;
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

    @Autowired
    MoviesService service;

    @Transactional
    @PostConstruct
    public Void initialize() {

        EditScreeningRequest screening1movie1 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA1)
                .movieId(1L)
                .startTime(LocalTime.of(8, 0))
                .movieType(MovieType.D3)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening2movie1 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA1)
                .movieId(1L)
                .startTime(LocalTime.of(12, 0))
                .movieType(MovieType.D3)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening3movie1 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA1)
                .movieId(1L)
                .startTime(LocalTime.of(16, 0))
                .movieType(MovieType.D2)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening1movie2 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA2)
                .movieId(2L)
                .startTime(LocalTime.of(9, 0))
                .movieType(MovieType.D2)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening2movie2 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA2)
                .movieId(2L)
                .startTime(LocalTime.of(14, 0))
                .movieType(MovieType.D2)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening3movie2 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA2)
                .movieId(2L)
                .startTime(LocalTime.of(20, 0))
                .movieType(MovieType.D2)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening1movie3 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA3)
                .movieId(3L)
                .startTime(LocalTime.of(12, 0))
                .movieType(MovieType.D2)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening2movie3 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA3)
                .movieId(3L)
                .startTime(LocalTime.of(15, 0))
                .movieType(MovieType.D2)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening3movie3 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA3)
                .movieId(3L)
                .startTime(LocalTime.of(20, 0))
                .movieType(MovieType.D2)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();


        for (int i = 1; i <= 31; i++) {
            EditRepertoireRequest editKrakowRepertoireRequest = EditRepertoireRequest.builder()
                    .city(City.KRAKOW)
                    .date(LocalDate.of(2023, 1, i))
                    .screenings(List.of(
                            screening1movie1, screening2movie1, screening3movie1,
                            screening1movie2, screening2movie2, screening3movie2,
                            screening1movie3, screening2movie3, screening3movie3))
                    .build();

            EditRepertoireRequest editKatowiceRepertoireRequest = EditRepertoireRequest.builder()
                    .city(City.KATOWICE)
                    .date(LocalDate.of(2023, 1, i))
                    .screenings(List.of(
                            screening1movie1, screening2movie1, screening3movie1,
                            screening1movie2, screening2movie2, screening3movie2,
                            screening1movie3, screening2movie3, screening3movie3))
                    .build();

            EditRepertoireRequest editOpoleRepertoireRequest = EditRepertoireRequest.builder()
                    .city(City.OPOLE)
                    .date(LocalDate.of(2023, 1, i))
                    .screenings(List.of(
                            screening1movie1, screening2movie1, screening3movie1,
                            screening1movie2, screening2movie2, screening3movie2,
                            screening1movie3, screening2movie3, screening3movie3))
                    .build();

            EditRepertoireRequest editWroclawRepertoireRequest = EditRepertoireRequest.builder()
                    .city(City.WROCLAW)
                    .date(LocalDate.of(2023, 1, i))
                    .screenings(List.of(
                            screening1movie1, screening2movie1, screening3movie1,
                            screening1movie2, screening2movie2, screening3movie2,
                            screening1movie3, screening2movie3, screening3movie3))
                    .build();

            EditRepertoireRequest editLubanRepertoireRequest = EditRepertoireRequest.builder()
                    .city(City.LUBAN)
                    .date(LocalDate.of(2023, 1, i))
                    .screenings(List.of(
                            screening1movie1, screening2movie1, screening3movie1,
                            screening1movie2, screening2movie2, screening3movie2,
                            screening1movie3, screening2movie3, screening3movie3))
                    .build();

            service.editRepertoire(editKrakowRepertoireRequest);
            service.editRepertoire(editKatowiceRepertoireRequest);
            service.editRepertoire(editOpoleRepertoireRequest);
            service.editRepertoire(editWroclawRepertoireRequest);
            service.editRepertoire(editLubanRepertoireRequest);
        }



        return null;
    }
}
