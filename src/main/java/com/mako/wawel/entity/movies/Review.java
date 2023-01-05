package com.mako.wawel.entity.movies;

import com.mako.wawel.entity.auth.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;

    @Column(name = "review_text")
    private String reviewText;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
