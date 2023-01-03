package com.mako.wawel.response.movies;

import com.mako.wawel.common.MovieSoundType;
import com.mako.wawel.common.MovieType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class GetScreeningResponse {
    private Long screenId;
    private Long movieId;
    private Long repertoireId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private MovieType movieType;
    private MovieSoundType movieSoundType;
    private String[][] seats;
}
