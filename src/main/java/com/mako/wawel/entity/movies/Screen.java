package com.mako.wawel.entity.movies;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "screen")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Screen {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int capacity;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(mappedBy = "screen")
    private List<Screening> screenings;
}
