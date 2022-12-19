package com.mako.wawel.request.movies;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class AddMovieRequest {

    private String title;

    private String genre;

    private int minAge;

    private int duration;

    private String description;
}
