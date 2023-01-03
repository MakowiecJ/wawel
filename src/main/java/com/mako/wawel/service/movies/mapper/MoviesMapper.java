package com.mako.wawel.service.movies.mapper;

import com.mako.wawel.entity.movies.Movie;
import com.mako.wawel.entity.movies.Review;
import com.mako.wawel.entity.movies.Screening;
import com.mako.wawel.entity.movies.Ticket;
import com.mako.wawel.response.TicketResponse;
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
                .movieId(review.getMovie().getId())
                .username(review.getUser().getUsername())
                .rating(review.getRating())
                .reviewText(review.getReviewText())
                .build();
    }

    public static TicketResponse toTicketResponse(final Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .city(ticket.getScreening().getScreen().getCinema().getCity())
                .date(ticket.getScreening().getDate())
                .startTime(ticket.getScreening().getStartTime())
                .movieTitle(ticket.getScreening().getMovie().getTitle())
                .screenName(ticket.getScreening().getScreen().getScreenName())
                .ticketType(ticket.getTicketType())
                .seatRow(ticket.getSeatRow())
                .seatNumber(ticket.getSeatNumber())
                .build();
    }
}
