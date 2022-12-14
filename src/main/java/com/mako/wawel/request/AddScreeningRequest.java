package com.mako.wawel.request;

import com.mako.wawel.common.City;
import com.mako.wawel.common.MovieSoundType;
import com.mako.wawel.common.MovieType;
import com.mako.wawel.common.ScreenName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class AddScreeningRequest {
    private City city;
    private ScreenName screenName;
    private Long movieId;
    private LocalDate date;
    private LocalTime startTime;
    private MovieType movieType;
    private MovieSoundType movieSoundType;
}
