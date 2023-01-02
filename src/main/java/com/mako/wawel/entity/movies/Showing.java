package com.mako.wawel.entity.movies;

import com.mako.wawel.common.MovieDimension;
import com.mako.wawel.common.MovieSoundType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "showings")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Showing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Movie movie;

    @Enumerated
    private MovieDimension dimension;

    @Column(name = "sound_type")
    @Enumerated
    private MovieSoundType soundType;

    private LocalTime startTime1;
    private LocalTime startTime2;
    private LocalTime startTime3;

    @ManyToOne
    private Repertoire repertoire;
}
