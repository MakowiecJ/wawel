package com.mako.wawel.response.movies;

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

    private double averageRating;

    private String description;
}