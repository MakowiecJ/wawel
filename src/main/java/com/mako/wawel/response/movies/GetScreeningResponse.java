package com.mako.wawel.response.movies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class GetScreeningResponse {
    private Long screenId;
    private Long movieId;
    private Long repertoireId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String[][] seats;
}
