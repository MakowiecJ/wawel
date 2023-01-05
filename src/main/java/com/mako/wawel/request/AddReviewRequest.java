package com.mako.wawel.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class AddReviewRequest {
    private Long userId;
    private Long movieId;
    private Integer rating;
    private String reviewText;
}
