package com.mako.wawel.service.mapper;

import com.mako.wawel.entity.movies.Movie;
import com.mako.wawel.entity.movies.Review;
import com.mako.wawel.entity.cinema.Ticket;
import com.mako.wawel.response.TicketResponse;
import com.mako.wawel.response.GeneralMovieResponse;
import com.mako.wawel.response.MovieReviewResponse;
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
                .status(movie.getStatus())
                .posterSource(movie.getPosterSource())
                .bigImageSource(movie.getBigImageSource())
                .trailerSource(movie.getTrailerSource())
                .description(movie.getDescription())
                .averageRating(movie.getAverageRating())
                .build();
    }

    public static MovieReviewResponse toMovieReviewResponse(final Review review) {
        return MovieReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .movieId(review.getMovie().getId())
                .username(review.getUser().getUsername())
                .rating(review.getRating())
                .reviewText(review.getReviewText())
                .build();
    }

    public static TicketResponse toTicketResponse(final Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .ticketType(ticket.getTicketType())
                .seatRow(ticket.getSeatRow())
                .seatNumber(ticket.getSeatNumber())
                .build();
    }

}
