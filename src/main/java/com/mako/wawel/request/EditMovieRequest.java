package com.mako.wawel.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.sql.Blob;

@Getter
@ToString
@AllArgsConstructor
public class EditMovieRequest {

    private Long movieId;

    private String title;

    private String genre;

    private int minAge;

    private int duration;

    private String posterSource;

    private String bigImageSource;

    private String trailerSource;

    private String description;
}
