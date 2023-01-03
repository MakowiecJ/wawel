package com.mako.wawel.response.movies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class MovieReviewResponse {
    private Long id;

    private Long movieId;

    private Long userId;

    private String username;

    private int rating;

    private String reviewText;
}
