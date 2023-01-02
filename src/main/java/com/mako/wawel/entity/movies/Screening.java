package com.mako.wawel.entity.movies;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "screening")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "repertoire_id")
    private Repertoire repertoire;

//    private Seat[][] seats;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
