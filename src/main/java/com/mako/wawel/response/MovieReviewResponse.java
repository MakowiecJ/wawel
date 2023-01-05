package com.mako.wawel.response;

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

    private Double rating;

    private String reviewText;
}
