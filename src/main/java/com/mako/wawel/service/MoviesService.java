package com.mako.wawel.service;

import com.mako.wawel.entity.auth.User;
import com.mako.wawel.entity.cinema.Cinema;
import com.mako.wawel.entity.cinema.Screen;
import com.mako.wawel.entity.cinema.Ticket;
import com.mako.wawel.entity.movies.*;
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

    public Void addScreening(AddScreeningRequest request) {
        Cinema cinema = cinemasRepository.findByCity(request.getCity());
        Screen screen = screensRepository.findByCinemaIdAndScreenName(cinema.getId(), request.getScreenName());
        Movie movie = moviesRepository.findById(request.getMovieId()).orElseThrow();
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

    public Void addRepertoire(AddRepertoireRequest request) {
        Cinema cinema = cinemasRepository.findByCity(request.getCity());

        repertoireRepository.save(Repertoire.builder()
                .cinema(cinema)
                .date(request.getDate())
                .build());
        return null;
    }

    public GetUserInfoResponse getUserInfo(Long userId) {
        User user = usersRepository.findById(userId).orElseThrow();
        return GetUserInfoResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
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

            User user = usersRepository.findById(request.getUserId()).orElseThrow();

            Ticket ticketEntity = Ticket.builder()
                    .user(user)
                    .screening(screening)
                    .seatRow(ticket.getSeatRow())
                    .seatNumber(ticket.getSeatNumber())
                    .ticketType(ticket.getTicketType())
                    .build();

            ticketsRepository.save(ticketEntity);
        }
        return new ResponseEntity<>("Pomyślnie zakupiono biliety!", HttpStatus.OK);
    }
}
