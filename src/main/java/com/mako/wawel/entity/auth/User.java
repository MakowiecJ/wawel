package com.mako.wawel.entity.auth;

import com.mako.wawel.entity.movies.Movie;
import com.mako.wawel.entity.movies.Review;
import com.mako.wawel.entity.cinema.Ticket;
import com.mako.wawel.response.GeneralMovieResponse;
import com.mako.wawel.service.mapper.MoviesMapper;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Setter
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    public List<GeneralMovieResponse> getWatchedMovies() {
        Set<Movie> movies = new HashSet<>();
        for (Ticket ticket : tickets) {
            if (ticket.getScreening().getRepertoire().getDate().isBefore(LocalDate.now())) {
                movies.add(ticket.getScreening().getMovie());
            }
        }

        return movies.stream()
                .map(MoviesMapper::toMovieResponse)
                .toList();
    }
}
