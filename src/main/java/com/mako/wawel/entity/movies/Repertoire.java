package com.mako.wawel.entity.movies;

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

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany
    @JoinColumn(name = "repertoire_id")
    private List<Screening> screenings;

}
