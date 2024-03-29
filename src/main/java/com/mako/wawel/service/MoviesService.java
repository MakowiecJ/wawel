package com.mako.wawel.service;

import com.mako.wawel.common.*;
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
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
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
        var response = toMovieResponse(moviesRepository.findById(movieId).orElseThrow());
        return response;
    }

    public Movie addMovie(final AddMovieRequest request) {
        return moviesRepository.save(Movie.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .minAge(request.getMinAge())
                .duration(request.getDuration())
                .posterSource(request.getPosterSource().getBytes(StandardCharsets.UTF_8))
                .bigImageSource(request.getBigImageSource().getBytes(StandardCharsets.UTF_8))
                .trailerSource(request.getTrailerSource())
                .status(Status.BRAK_SEANSU)
                .description(request.getDescription())
                .reviews(new ArrayList<>())
                .screenings(new ArrayList<>())
                .build());
    }

    public ResponseEntity<String> deleteMovie(final Long movieId) {
        Optional<Movie> toDelete = moviesRepository.findById(movieId);
        if (toDelete.isEmpty()) {
            return new ResponseEntity<>("Nie znaleziono filmu o podanym id", HttpStatus.NOT_FOUND);
        }
        moviesRepository.delete(toDelete.get());
        return new ResponseEntity<>("Pomyślnie usunięto film", HttpStatus.OK);
    }

    public List<MovieReviewResponse> getMovieReviews(Long movieId) {
        return moviesRepository.findById(movieId).orElseThrow().getReviews().stream()
                .map(MoviesMapper::toMovieReviewResponse)
                .toList();
    }

    public ResponseEntity<String> addReview(AddReviewRequest request) {

        if (request.getReviewText() != null && request.getRating() == null) {
            return new ResponseEntity<>("Nie można dodać recenzji bez oceny", HttpStatus.BAD_REQUEST);
        }

        if (request.getRating() < 1 || request.getRating() > 5) {
            return new ResponseEntity<>("Ocena może być liczbą całkowitą z zakresu [1-5]", HttpStatus.BAD_REQUEST);
        }

        Review review = reviewsRepository.findByMovieIdAndUserId(request.getMovieId(), request.getUserId());
        Movie movie = moviesRepository.findById(request.getMovieId()).orElseThrow();
        User user = usersRepository.findById(request.getUserId()).orElseThrow();


        if (review != null) {
            review.setRating(request.getRating());
            if (request.getReviewText() != null) {
                review.setReviewText(request.getReviewText());
            }
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
        return new ResponseEntity<>("Pomyślnie dodano recenzję", HttpStatus.OK);
    }

    public ResponseEntity<?> getRepertoire(GetRepertoireRequest request) {
        Cinema cinema = cinemasRepository.findByCity(request.getCity());
        Optional<Repertoire> repertoire = repertoireRepository.findByCinemaAndDate(cinema, request.getDate());

        if (repertoire.isEmpty()) {
            return new ResponseEntity<>(addRepertoire(AddRepertoireRequest.of(request.getCity(), request.getDate())), HttpStatus.OK);
        }

        List<RepertoireItem> items = new ArrayList<>();

        List<Long> movieIds = new ArrayList<>();
        Map<Long, GeneralMovieResponse> idToMovie = new HashMap<>();
        Map<Long, List<ScreeningItem>> screeningToMovie = new HashMap<>();


        if (repertoire.get().getScreenings() != null) {
            for (Screening screening : repertoire.get().getScreenings()) {
                if (screening.getRepertoire().getDate().equals(request.getDate())) {
                    if (!movieIds.contains(screening.getMovie().getId())) {
                        movieIds.add(screening.getMovie().getId());
                        idToMovie.put(screening.getMovie().getId(), MoviesMapper.toMovieResponse(screening.getMovie()));

                        screeningToMovie.put(screening.getMovie().getId(), List.of(ScreeningItem.builder()
                                .screeningId(screening.getId())
                                .startTime(screening.getStartTime())
                                .screenName(screening.getScreen().getScreenName())
                                .movieType(screening.getMovieType())
                                .movieSoundType(screening.getMovieSoundType())
                                .build()));
                    } else {
                        List<ScreeningItem> screeningItems = new ArrayList<>(screeningToMovie.get(screening.getMovie().getId()));

                        screeningItems.add(ScreeningItem.builder()
                                .screeningId(screening.getId())
                                .startTime(screening.getStartTime())
                                .screenName(screening.getScreen().getScreenName())
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

        }
        return new ResponseEntity<>(GetRepertoireResponse.of(request.getCity(), request.getDate(), items), HttpStatus.OK);

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
            return new ResponseEntity<>("Nie można dodać filmu, który jest zarchiwizowany", HttpStatus.BAD_REQUEST);
        }

        Cinema cinema = cinemasRepository.findByCity(request.getCity());
        Screen screen = screensRepository.findByCinemaIdAndScreenName(cinema.getId(), request.getScreenName());
        Optional<Repertoire> repertoire = repertoireRepository.findByCinemaAndDate(cinema, request.getDate());

        if (repertoire.isEmpty()) {
            Repertoire newRepertoire = repertoireRepository.save(Repertoire.builder()
                    .cinema(cinema)
                    .date(request.getDate())
                    .build());

            Screening screening = Screening.builder()
                    .screen(screen)
                    .movie(movie)
                    .repertoire(newRepertoire)
                    .startTime(request.getStartTime())
                    .movieType(request.getMovieType())
                    .movieSoundType(request.getMovieSoundType())
                    .seats(Screening.newSeats())
                    .build();

            screeningsRepository.save(screening);

        } else {
            Screening screening = Screening.builder()
                    .screen(screen)
                    .movie(movie)
                    .repertoire(repertoire.get())
                    .startTime(request.getStartTime())
                    .movieType(request.getMovieType())
                    .movieSoundType(request.getMovieSoundType())
                    .seats(Screening.newSeats())
                    .build();

            screeningsRepository.save(screening);
        }

        return null;
    }

    public GetRepertoireResponse addRepertoire(AddRepertoireRequest request) {
        Cinema cinema = cinemasRepository.findByCity(request.getCity());

        repertoireRepository.save(Repertoire.builder()
                .cinema(cinema)
                .date(request.getDate())
                .build());
        return GetRepertoireResponse.of(request.getCity(), request.getDate(), new ArrayList<>());
    }

    public GetUserInfoResponse getUserInfo(Long userId) {
        User user = usersRepository.findById(userId).orElseThrow();
        List<TicketsByScreeningResponse> groupedTickets = new ArrayList<>();
        Map<Long, TicketsByScreeningResponse> screeningIdToGroupedTickets = new HashMap<>();
        for (Ticket ticket : user.getTickets()) {
            Long screeningId = ticket.getScreening().getId();
            if (!screeningIdToGroupedTickets.containsKey(screeningId)) {
                screeningIdToGroupedTickets.put(screeningId, TicketsByScreeningResponse.builder()
                        .screeningId(screeningId)
                        .city(ticket.getScreening().getRepertoire().getCinema().getCity())
                        .date(ticket.getScreening().getRepertoire().getDate())
                        .startTime(ticket.getScreening().getStartTime())
                        .movieTitle(ticket.getScreening().getMovie().getTitle())
                        .movieId(ticket.getScreening().getMovie().getId())
                        .screenName(ticket.getScreening().getScreen().getScreenName())
                        .tickets(List.of(MoviesMapper.toTicketResponse(ticket)))
                        .build());
            } else {

                TicketsByScreeningResponse updatedTicket=  new TicketsByScreeningResponse(screeningIdToGroupedTickets.get(screeningId));
                updatedTicket.addTicket(MoviesMapper.toTicketResponse(ticket));

                screeningIdToGroupedTickets.replace(screeningId, updatedTicket);
            }
        }

        for (Map.Entry<Long, TicketsByScreeningResponse> entry : screeningIdToGroupedTickets.entrySet()) {
            groupedTickets.add(entry.getValue());
        }

        return GetUserInfoResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .tickets(groupedTickets)
                .reviews(user.getReviews().stream().map(MoviesMapper::toMovieReviewResponse).toList())
                .watchedMovies(user.getWatchedMovies())
                .build();

    }

    public ResponseEntity<String> buyTickets(BuyTicketsRequest request) {
        for (BuyTicketInfo ticket : request.getTickets()) {
            Screening screening = screeningsRepository.findById(request.getScreeningId()).orElseThrow();
            String[][] seats = screening.getSeats();
            if (screening.getRepertoire().getDate().isBefore(LocalDate.now())) {
                return new ResponseEntity<>("Seans już się odbył", HttpStatus.BAD_REQUEST);
            }

            if (seats[ticket.getSeatRow()][ticket.getSeatNumber()].equals(ZAJETE.name())
                    || seats[ticket.getSeatRow()][ticket.getSeatNumber()].equals(NIE_ISTNIEJE.name())) {
                return new ResponseEntity<>(
                        "Miejsce: [" + ticket.getSeatRow() + "][" + ticket.getSeatNumber() + "] zajęte bądź nie istnieje",
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
        return new ResponseEntity<>("Pomyślnie zakupiono biliety", HttpStatus.OK);
    }

    public ResponseEntity<String> archiveMovie(Long movieId) {
        Movie movie = moviesRepository.findById(movieId).orElseThrow();
        movie.setStatus(Status.ZARCHIWIZOWANY);
        moviesRepository.save(movie);

        return new ResponseEntity<>("Pomyślnie zarchiwizowano film", HttpStatus.OK);
    }

    public ResponseEntity<?> getReview(Long reviewId) {
        Optional<Review> review = reviewsRepository.findById(reviewId);

        if (review.isEmpty()) {
            return new ResponseEntity<>("Nie znaleziono takiej recenzji", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(MoviesMapper.toMovieReviewResponse(review.get()), HttpStatus.OK);
        }
    }

    public ResponseEntity<String> editMovie(EditMovieRequest request) {
        Optional<Movie> movie = moviesRepository.findById(request.getMovieId());
        if (movie.isEmpty()) {
            return new ResponseEntity<>("Film o podanym id nie istnieje", HttpStatus.BAD_REQUEST);
        }
        Movie movieEntity = movie.get();
        movieEntity.setTitle(request.getTitle());
        movieEntity.setGenre(request.getGenre());
        movieEntity.setMinAge(request.getMinAge());
        movieEntity.setDuration(request.getDuration());
        movieEntity.setPosterSource(request.getPosterSource().getBytes(StandardCharsets.UTF_8));
        movieEntity.setBigImageSource(request.getBigImageSource().getBytes(StandardCharsets.UTF_8));
        movieEntity.setTrailerSource(request.getTrailerSource());
        movieEntity.setDescription(request.getDescription());

        moviesRepository.save(movieEntity);
        return new ResponseEntity<>("Pomyślnie edytowano film", HttpStatus.OK);
    }

    public ResponseEntity<?> editRepertoire(EditRepertoireRequest request) {
        Cinema cinema = cinemasRepository.findByCity(request.getCity());
        Optional<Repertoire> repertoire = repertoireRepository.findByCinemaAndDate(cinema, request.getDate());

        if (repertoire.isEmpty()) {
            addRepertoire(AddRepertoireRequest.of(request.getCity(), request.getDate()));
        }

        for (EditScreeningRequest editScreeningRequest : request.getScreenings()) {
            editScreening(editScreeningRequest, request.getCity(), request.getDate());
        }

        return new ResponseEntity<>("Pomyślnie edytowano repertuar", HttpStatus.OK);
    }

    public ResponseEntity<?> editScreening(final EditScreeningRequest request, final City city, final LocalDate date) {

        if (request.getScreeningId() == null || screeningsRepository.findById(request.getScreeningId()).isEmpty()) {
            addScreening(AddScreeningRequest.builder()
                    .city(city)
                    .screenName(request.getScreenName())
                    .movieId(request.getMovieId())
                    .date(date)
                    .startTime(request.getStartTime())
                    .movieType(request.getMovieType())
                    .movieSoundType(request.getMovieSoundType())
                    .build());
            return new ResponseEntity<>("Pomyślnie dodano seans", HttpStatus.OK);
        }
        Optional<Screening> screening = screeningsRepository.findById(request.getScreeningId());

        Screening screeningEntity = screening.get();

        Cinema cinema = screeningEntity.getRepertoire().getCinema();
        Screen screen = screensRepository.findByCinemaIdAndScreenName(cinema.getId(), request.getScreenName());
        Optional<Movie> movie = moviesRepository.findById(request.getMovieId());
        if (movie.isEmpty()) {
            return new ResponseEntity<>("Nie znaleziono takiego filmu", HttpStatus.NOT_FOUND);
        }

        screeningEntity.setScreen(screen);
        screeningEntity.setMovie(movie.get());
        screeningEntity.setStartTime(request.getStartTime());
        screeningEntity.setMovieType(request.getMovieType());
        screeningEntity.setMovieSoundType(request.getMovieSoundType());

        screeningsRepository.save(screeningEntity);

        return new ResponseEntity<>("Pomyślnie edytowano seans", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteScreening(Long screeningId) {
        Optional<Screening> screening = screeningsRepository.findById(screeningId);
        if (screening.isEmpty()) {
            return new ResponseEntity<>("Nie znaleziono seansu o id: " + screeningId, HttpStatus.NOT_FOUND);
        }
        screeningsRepository.delete(screening.get());
        return new ResponseEntity<>("Pomyślnie usunięto seans", HttpStatus.OK);
    }


    public Void initialize(final int month, final int startDay, final int endDay) {
        EditScreeningRequest screening1movie1 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA1)
                .movieId(1L)
                .startTime(LocalTime.of(10, 0))
                .movieType(MovieType.D3)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening2movie1 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA1)
                .movieId(1L)
                .startTime(LocalTime.of(14, 0))
                .movieType(MovieType.D3)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening3movie1 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA1)
                .movieId(1L)
                .startTime(LocalTime.of(18, 0))
                .movieType(MovieType.D2)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening1movie2 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA2)
                .movieId(2L)
                .startTime(LocalTime.of(11, 0))
                .movieType(MovieType.D2)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening2movie2 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA2)
                .movieId(2L)
                .startTime(LocalTime.of(14, 0))
                .movieType(MovieType.D2)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening3movie2 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA2)
                .movieId(2L)
                .startTime(LocalTime.of(20, 0))
                .movieType(MovieType.D2)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening1movie3 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA3)
                .movieId(3L)
                .startTime(LocalTime.of(12, 0))
                .movieType(MovieType.D2)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening2movie3 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA3)
                .movieId(3L)
                .startTime(LocalTime.of(15, 0))
                .movieType(MovieType.D2)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();

        EditScreeningRequest screening3movie3 = EditScreeningRequest.builder()
                .screenName(ScreenName.SALA3)
                .movieId(3L)
                .startTime(LocalTime.of(21, 0))
                .movieType(MovieType.D2)
                .movieSoundType(MovieSoundType.DUBBING)
                .build();


        for (int i = startDay; i <= endDay; i++) {
            EditRepertoireRequest editKrakowRepertoireRequest = EditRepertoireRequest.builder()
                    .city(City.KRAKOW)
                    .date(LocalDate.of(2023, month, i))
                    .screenings(List.of(
                            screening1movie1, screening2movie1, screening3movie1,
                            screening1movie2, screening2movie2, screening3movie2,
                            screening1movie3, screening2movie3, screening3movie3))
                    .build();

            EditRepertoireRequest editKatowiceRepertoireRequest = EditRepertoireRequest.builder()
                    .city(City.KATOWICE)
                    .date(LocalDate.of(2023, month, i))
                    .screenings(List.of(
                            screening1movie1, screening2movie1, screening3movie1,
                            screening1movie2, screening2movie2, screening3movie2,
                            screening1movie3, screening2movie3, screening3movie3))
                    .build();

            EditRepertoireRequest editOpoleRepertoireRequest = EditRepertoireRequest.builder()
                    .city(City.OPOLE)
                    .date(LocalDate.of(2023, month, i))
                    .screenings(List.of(
                            screening1movie1, screening2movie1, screening3movie1,
                            screening1movie2, screening2movie2, screening3movie2,
                            screening1movie3, screening2movie3, screening3movie3))
                    .build();

            EditRepertoireRequest editWroclawRepertoireRequest = EditRepertoireRequest.builder()
                    .city(City.WROCLAW)
                    .date(LocalDate.of(2023, month, i))
                    .screenings(List.of(
                            screening1movie1, screening2movie1, screening3movie1,
                            screening1movie2, screening2movie2, screening3movie2,
                            screening1movie3, screening2movie3, screening3movie3))
                    .build();

            EditRepertoireRequest editLubanRepertoireRequest = EditRepertoireRequest.builder()
                    .city(City.LUBAN)
                    .date(LocalDate.of(2023, month, i))
                    .screenings(List.of(
                            screening1movie1, screening2movie1, screening3movie1,
                            screening1movie2, screening2movie2, screening3movie2,
                            screening1movie3, screening2movie3, screening3movie3))
                    .build();

            editRepertoire(editKrakowRepertoireRequest);
            editRepertoire(editKatowiceRepertoireRequest);
            editRepertoire(editOpoleRepertoireRequest);
            editRepertoire(editWroclawRepertoireRequest);
            editRepertoire(editLubanRepertoireRequest);
        }
        return null;
    }
}
