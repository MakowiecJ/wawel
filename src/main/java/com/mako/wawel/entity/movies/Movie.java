package com.mako.wawel.entity.movies;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String genre;

    @Column(name = "min_age")
    private int minAge;

    private int duration;

    private String description;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews;

    public double getAverageRating() {
        double totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }
        return totalRating / reviews.size();
    }
}
