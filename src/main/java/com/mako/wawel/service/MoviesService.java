package com.mako.wawel.service;

import com.mako.wawel.common.Status;
import com.mako.wawel.entity.auth.User;
import com.mako.wawel.entity.cinema.Cinema;
import com.mako.wawel.entity.cinema.Screen;
import com.mako.wawel.entity.cinema.Ticket;
import com.mako.wawel.entity.movies.Movie;
import com.mako.wawel.entity.movies.Repertoire;
import com.mako.wawel.entity.movies.Review;
import com.mako.wawel.entity.movies.Screening;
import com.mako.wawel.persistence.repositories.*;
import com.mako.wawel.persistence.repositories.auth.UsersRepository;
import com.mako.wawel.request.*;
import com.mako.wawel.response.*;
import com.mako.wawel.service.mapper.MoviesMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static com.mako.wawel.common.Availability.NIE_ISTNIEJE;
import static com.mako.wawel.common.Availability.ZAJETE;
import static com.mako.wawel.service.mapper.MoviesMapper.toMovieResponse;

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
    private RepertoireRepository repertoireRepository;

    @Autowired
    private CinemaRepository cinemasRepository;

    @Autowired
    private ScreensRepository screensRepository;

    @Autowired
    private TicketsRepository ticketsRepository;

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
                .posterSource(request.getPosterSource())
                .trailerSource(request.getTrailerSource())
                .status(Status.BRAK_SEANSU)
                .description(request.getDescription())
                .reviews(new ArrayList<>())
                .screenings(new ArrayList<>())
                .build());
    }

    public ResponseEntity<String> deleteMovie(final Long movieId) {
        Optional<Movie> toDelete = moviesRepository.findById(movieId);
        moviesRepository.delete(toDelete.orElseThrow());
        return new ResponseEntity<>("Pomyślnie usunięto film!", HttpStatus.OK);
    }

    public List<MovieReviewResponse> getMovieReviews(Long movieId) {
        return moviesRepository.findById(movieId).orElseThrow().getReviews().stream()
                .map(MoviesMapper::toMovieReviewResponse)
                .toList();
    }

    public ResponseEntity<String> addReview(AddReviewRequest request) {

        if (request.getReviewText() != null && request.getRating() == null) {
            return new ResponseEntity<>("Nie można dodać recenzji bez oceny!", HttpStatus.BAD_REQUEST);
        }

        if (request.getRating() < 1 || request.getRating() > 5) {
            return new ResponseEntity<>("Ocena może być liczbą całkowitą z zakresu [1-5]!", HttpStatus.BAD_REQUEST);
        }

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
        return new ResponseEntity<>("Pomyślnie dodano recenzję!", HttpStatus.OK);
    }

    public GetRepertoireResponse getRepertoire(GetRepertoireRequest request) {
        Cinema cinema = cinemasRepository.findByCity(request.getCity());
        Repertoire repertoire = repertoireRepository.findByCinemaAndDate(cinema, request.getDate());

        List<RepertoireItem> items = new ArrayList<>();

        List<Long> movieIds = new ArrayList<>();
        Map<Long, GeneralMovieResponse> idToMovie = new HashMap<>();
        Map<Long, List<ScreeningItem>> screeningToMovie = new HashMap<>();

        for (Screening screening : repertoire.getScreenings()) {
            if (screening.getRepertoire().getDate().equals(request.getDate())) {
                if (!movieIds.contains(screening.getMovie().getId())) {
                    movieIds.add(screening.getMovie().getId());
                    idToMovie.put(screening.getMovie().getId(), MoviesMapper.toMovieResponse(screening.getMovie()));

                    screeningToMovie.put(screening.getMovie().getId(), List.of(ScreeningItem.builder()
                            .screeningId(screening.getId())
                            .startTime(screening.getStartTime())
                            .movieType(screening.getMovieType())
                            .movieSoundType(screening.getMovieSoundType())
                            .build()));
                } else {
                    List<ScreeningItem> screeningItems = new ArrayList<>(screeningToMovie.get(screening.getMovie().getId()));

                    screeningItems.add(ScreeningItem.builder()
                            .screeningId(screening.getId())
                            .startTime(screening.getStartTime())
                            .movieType(screening.getMovieType())
                            .movieSoundType(screening.getMovieSoundType())
                            .build());
                    screeningToMovie.replace(screening.getMovie().getId(), screeningItems);
                }
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
                .date(screening.getRepertoire().getDate())
                .startTime(screening.getStartTime())
                .movieType(screening.getMovieType())
                .movieSoundType(screening.getMovieSoundType())
                .seats(screening.getSeats())
                .build();
    }

    public ResponseEntity<String> addScreening(AddScreeningRequest request) {
        Movie movie = moviesRepository.findById(request.getMovieId()).orElseThrow();

        if (movie.getStatus().equals(Status.ZARCHIWIZOWANY)) {
            return new ResponseEntity<>("Nie można dodać filmu, który jest zarchiwizowany!", HttpStatus.BAD_REQUEST);
        }

        Cinema cinema = cinemasRepository.findByCity(request.getCity());
        Screen screen = screensRepository.findByCinemaIdAndScreenName(cinema.getId(), request.getScreenName());
        Repertoire repertoire = repertoireRepository.findByCinemaAndDate(cinema, request.getDate());

        Screening screening = Screening.builder()
                .screen(screen)
                .movie(movie)
                .repertoire(repertoire)
                .startTime(request.getStartTime())
                .movieType(request.getMovieType())
                .movieSoundType(request.getMovieSoundType())
                .seats(Screening.newSeats())
                .build();

        screeningsRepository.save(screening);
        return null;
    }

    public ResponseEntity<String> addRepertoire(AddRepertoireRequest request) {
        Cinema cinema = cinemasRepository.findByCity(request.getCity());

        repertoireRepository.save(Repertoire.builder()
                .cinema(cinema)
                .date(request.getDate())
                .build());
        return new ResponseEntity<>("Pomyślnie dodano repertuar!", HttpStatus.OK);
    }

    public GetUserInfoResponse getUserInfo(Long userId) {
        User user = usersRepository.findById(userId).orElseThrow();
        return GetUserInfoResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .tickets(user.getTickets().stream().map(MoviesMapper::toTicketResponse).toList())
                .reviews(user.getReviews().stream().map(MoviesMapper::toMovieReviewResponse).toList())
                .watchedMovies(user.getWatchedMovies())
                .build();

    }

    public ResponseEntity<String> buyTickets(BuyTicketsRequest request) {
        for (BuyTicketInfo ticket : request.getTickets()) {
            Screening screening = screeningsRepository.findById(request.getScreeningId()).orElseThrow();
            String[][] seats = screening.getSeats();
            if (screening.getRepertoire().getDate().isBefore(LocalDate.now())) {
                return new ResponseEntity<>("Seans już się odbył!", HttpStatus.BAD_REQUEST);
            }

            if (seats[ticket.getSeatRow()][ticket.getSeatNumber()].equals(ZAJETE.name())
                    || seats[ticket.getSeatRow()][ticket.getSeatNumber()].equals(NIE_ISTNIEJE.name())) {
                return new ResponseEntity<>(
                        "Miejsce: [" + ticket.getSeatRow() + "][" + ticket.getSeatNumber() + "] zajęte bądź nie istnieje!",
                        HttpStatus.BAD_REQUEST);
            }


            screening.changeSeatStatus(ticket.getSeatRow(), ticket.getSeatNumber());

            Ticket ticketEntity;

            if (request.getUserId() != null) {
                User user = usersRepository.findById(request.getUserId()).orElseThrow();

                ticketEntity = Ticket.builder()
                        .user(user)
                        .screening(screening)
                        .seatRow(ticket.getSeatRow())
                        .seatNumber(ticket.getSeatNumber())
                        .ticketType(ticket.getTicketType())
                        .build();
            } else {
                ticketEntity = Ticket.builder()
                        .user(null)
                        .screening(screening)
                        .seatRow(ticket.getSeatRow())
                        .seatNumber(ticket.getSeatNumber())
                        .ticketType(ticket.getTicketType())
                        .build();
            }


            ticketsRepository.save(ticketEntity);
        }
        return new ResponseEntity<>("Pomyślnie zakupiono biliety!", HttpStatus.OK);
    }

    public ResponseEntity<String> archiveMovie(Long movieId) {
        Movie movie = moviesRepository.findById(movieId).orElseThrow();
        movie.setStatus(Status.ZARCHIWIZOWANY);
        moviesRepository.save(movie);

        return new ResponseEntity<>("Pomyślnie zarchiwizowano film!", HttpStatus.OK);
    }
}
