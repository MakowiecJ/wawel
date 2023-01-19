package com.mako.wawel.entity.movies;

import com.mako.wawel.entity.cinema.Cinema;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "repertoire")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Repertoire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @Column(name = "date")
    private LocalDate date;

    @OneToMany
    @JoinColumn(name = "repertoire_id")
    private List<Screening> screenings;

}
