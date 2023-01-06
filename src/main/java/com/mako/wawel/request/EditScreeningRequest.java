package com.mako.wawel.request;

import com.mako.wawel.common.MovieSoundType;
import com.mako.wawel.common.MovieType;
import com.mako.wawel.common.ScreenName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@ToString
@AllArgsConstructor
public class EditScreeningRequest {
    private Long screeningId;
    private ScreenName screenName;
    private Long movieId;
    private LocalTime startTime;
    private MovieType movieType;
    private MovieSoundType movieSoundType;
}
