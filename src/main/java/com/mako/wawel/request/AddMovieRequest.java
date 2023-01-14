package com.mako.wawel.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.sql.Blob;

@Getter
@ToString
@AllArgsConstructor
public class AddMovieRequest {

    private String title;

    private String genre;

    private int minAge;

    private int duration;

    private Blob posterSource;

    private Blob bigImageSource;

    private String trailerSource;

    private String description;
}
