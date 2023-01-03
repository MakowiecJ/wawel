package com.mako.wawel.service.movies;

import com.mako.wawel.entity.auth.User;
import com.mako.wawel.entity.movies.*;
import com.mako.wawel.persistence.auth.UsersRepository;
import com.mako.wawel.persistence.movies.*;
import com.mako.wawel.request.movies.*;
import com.mako.wawel.response.movies.*;
import com.mako.wawel.service.movies.mapper.MoviesMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.mako.wawel.service.movies.mapper.MoviesMapper.toMovieResponse;

@Service
@AllArgsConstructor
public class MoviesService {

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private ScreeningsRepository screeningsRepository;

    @Autowired
    private RepertoiresRepository repertoiresRepository;

    @Autowired
    private CinemaRepository cinemasRepository;

    @Autowired
    private ScreensRepository screensRepository;

    public List<GeneralMovieResponse> getMovies() {
        return moviesRepository.findAll().stream()
                .map(MoviesMapper::toMovieResponse)
                .toList();
    }

    public GeneralMovieResponse getMovie(final Long movieId) {
        return toMovieResponse(moviesRepository.findById(movieId).orElseThrow());
    }

    public Movie addMovie(final AddMovieRequest request) {
        return moviesRepository.save(Movie.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .minAge(request.getMinAge())
                .duration(request.getDuration())
                .description(request.getDescription())
                .reviews(new ArrayList<>())
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
        return null;
    }

    public GetRepertoireResponse getRepertoire(GetRepertoireRequest request) {
        Cinema cinema = cinemasRepository.findByCity(request.getCity());
        Repertoire repertoire = repertoiresRepository.findByCinemaAndDate(cinema, request.getDate());

        List<RepertoireItem> items = new ArrayList<>();

        List<Long> movieIds = new ArrayList<>();
        Map<Long, GeneralMovieResponse> idToMovie = new HashMap<>();
        Map<Long, List<ScreeningItem>> screeningToMovie = new HashMap<>();

        for (Screening screening : repertoire.getScreenings()) {

            if (!movieIds.contains(screening.getMovie().getId())) {
                movieIds.add(screening.getMovie().getId());
                idToMovie.put(screening.getMovie().getId(), MoviesMapper.toMovieResponse(screening.getMovie()));

                screeningToMovie.put(screening.getMovie().getId(), List.of(ScreeningItem.builder()
                        .screeningId(screening.getId())
                        .startTime(screening.getStartTime())
                        .endTime(screening.getEndTime())
                        .build()));
            } else {
                List<ScreeningItem> screeningItems = new ArrayList<>(screeningToMovie.get(screening.getMovie().getId()));

                screeningItems.add(ScreeningItem.builder()
                        .screeningId(screening.getId())
                        .startTime(screening.getStartTime())
                        .endTime(screening.getEndTime())
                        .build());
                screeningToMovie.replace(screening.getMovie().getId(), screeningItems);
            }

        }
        for (Map.Entry<Long, List<ScreeningItem>> entry : screeningToMovie.entrySet()) {
            items.add(new RepertoireItem(idToMovie.get(entry.getKey()), entry.getValue()));
        }

        return new GetRepertoireResponse(items);
    }



    public GetScreeningResponse getScreening(final Long screeningId) {
        Screening screening = screeningsRepository.findById(screeningId).orElseThrow();
        return GetScreeningResponse.builder()
                .screenId(screeningId)
                .movieId(screening.getMovie().getId())
                .repertoireId(screening.getRepertoire().getId())
                .startTime(screening.getStartTime())
                .endTime(screening.getEndTime())
                .seats(screening.getSeats())
                .build();
    }

    public Void addScreening(AddScreeningRequest request) {
        Cinema cinema = cinemasRepository.findByCity(request.getCity());
        Screen screen = screensRepository.findByCinemaIdAndScreenName(cinema.getId(), request.getScreenName());
        Movie movie = moviesRepository.findById(request.getMovieId()).orElseThrow();
        Repertoire repertoire = repertoiresRepository.findByCinemaAndDate(cinema, request.getDate());
        Screening screening = Screening.builder()
                .screen(screen)
                .movie(movie)
                .repertoire(repertoire)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .seats(Screening.newSeats())
                .build();

        screeningsRepository.save(screening);
        return null;
    }

    public Void addRepertoire(AddRepertoireRequest request) {
        Cinema cinema = cinemasRepository.findByCity(request.getCity());

        repertoiresRepository.save(Repertoire.builder()
                .cinema(cinema)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build());
        return null;
    }
}
