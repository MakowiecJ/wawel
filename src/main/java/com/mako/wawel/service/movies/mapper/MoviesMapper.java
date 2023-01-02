package com.mako.wawel.service.movies.mapper;

import com.mako.wawel.entity.movies.Movie;
import com.mako.wawel.entity.movies.Review;
import com.mako.wawel.response.movies.GeneralMovieResponse;
import com.mako.wawel.response.movies.MovieReviewResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MoviesMapper {

    public static GeneralMovieResponse toMovieResponse(final Movie movie) {
        return GeneralMovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .minAge(movie.getMinAge())
                .duration(movie.getDuration())
                .description(movie.getDescription())
                .averageRating(movie.getAverageRating())
                .build();
    }

    public static MovieReviewResponse toMovieReviewResponse(final Review review) {
        return MovieReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .username(review.getUser().getUsername())
                .rating(review.getRating())
                .reviewText(review.getReviewText())
                .build();
    }
}
