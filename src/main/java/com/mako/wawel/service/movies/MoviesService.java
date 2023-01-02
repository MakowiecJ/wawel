package com.mako.wawel.service.movies;

import com.mako.wawel.entity.auth.User;
import com.mako.wawel.entity.movies.Movie;
import com.mako.wawel.entity.movies.Review;
import com.mako.wawel.persistence.auth.UsersRepository;
import com.mako.wawel.persistence.movies.MoviesRepository;
import com.mako.wawel.persistence.movies.ReviewsRepository;
import com.mako.wawel.request.movies.AddMovieRequest;
import com.mako.wawel.request.movies.AddReviewRequest;
import com.mako.wawel.request.movies.GetRepertoireRequest;
import com.mako.wawel.response.movies.GeneralMovieResponse;
import com.mako.wawel.response.movies.GetRepertoireResponse;
import com.mako.wawel.response.movies.MovieReviewResponse;
import com.mako.wawel.service.movies.mapper.MoviesMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MoviesService {

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ReviewsRepository reviewsRepository;

    public List<GeneralMovieResponse> getMovies() {
        return moviesRepository.findAll().stream()
                .map(MoviesMapper::toMovieResponse)
                .toList();
    }

    public Movie getMovie(final Long movieId) {
        return moviesRepository.findById(movieId).orElseThrow();
    }

    public Movie addMovie(final AddMovieRequest request) {
        return moviesRepository.save(Movie.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .minAge(request.getMinAge())
                .duration(request.getDuration())
                .description(request.getDescription())
                .build());
    }

    public void deleteMovie(final Long movieId) {
        Optional<Movie> toDelete = moviesRepository.findById(movieId);
        moviesRepository.delete(toDelete.orElseThrow());
    }

    public List<MovieReviewResponse> getMovieReviews(Long movieId) {
        return moviesRepository.findById(movieId).orElseThrow().getReviews().stream()
                .map(MoviesMapper::toMovieReviewResponse)
                .toList();
    }

    public Void addReview(AddReviewRequest request) {
        Review review = reviewsRepository.findByMovieIdAndUserId(request.getMovieId(), request.getUserId());
        Movie movie = moviesRepository.findById(request.getMovieId()).orElseThrow();
        User user = usersRepository.findById(request.getUserId()).orElseThrow();

        if (review != null) {
            review.setRating(request.getRating());
            review.setReviewText(request.getReviewText());
            reviewsRepository.save(review);
        } else {
            Review newReview = Review.builder()
                    .rating(request.getRating())
                    .reviewText(request.getReviewText())
                    .movie(movie)
                    .user(user)
                    .build();
            reviewsRepository.save(newReview);

        }
//        Review review = Review.builder()
//                .rating(request.getRating())
//                .reviewText(request.getReviewText())
//                .movie(movie)
//                .user(user)
//                .build();
//        movie.getReviews().add(review);
//        user.getReviews().add(review);
//
//        moviesRepository.save(movie);
//        usersRepository.save(user);
//        reviewsRepository.save(review);

//        reviewsRepository.save(Review.builder()
//                .rating(request.getRating())
//                .reviewText(request.getReviewText())
//                .movie(movie)
//                .user(user)
//                .build());
        return null;
    }

    public GetRepertoireResponse getRepertoire(GetRepertoireRequest request) {

        return null;
    }
}
