package com.mako.wawel.response;

import com.mako.wawel.common.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class GeneralMovieResponse {
    private Long id;

    private String title;

    private String genre;

    private int minAge;

    private int duration;

    private Double averageRating;

    private Status status;

    private String posterSource;

    private String trailerSource;

    private String description;
}
