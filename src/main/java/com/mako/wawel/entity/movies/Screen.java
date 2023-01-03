package com.mako.wawel.entity.movies;

import com.mako.wawel.common.ScreenName;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "screen")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Screen {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "screen_name")
    @Enumerated(EnumType.STRING)
    private ScreenName screenName;

    private int capacity;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(mappedBy = "screen")
    private List<Screening> screenings;
}
