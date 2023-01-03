package com.mako.wawel.entity.auth;

import com.mako.wawel.entity.movies.Review;
import com.mako.wawel.entity.cinema.Ticket;
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    public Set<Long> getWatchedMovies() {
        Set<Long> movieIds = new HashSet<>();
        for (Ticket ticket : tickets) {
            if (ticket.getScreening().getDate().isBefore(LocalDate.now())) {
                movieIds.add(ticket.getScreening().getMovie().getId());
            }
        }
        return movieIds;
    }
}
