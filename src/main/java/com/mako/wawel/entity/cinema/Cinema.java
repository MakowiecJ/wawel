package com.mako.wawel.entity.cinema;

import com.mako.wawel.common.City;
import com.mako.wawel.entity.movies.Repertoire;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cinema")
@Getter
@Setter
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    @Enumerated(EnumType.STRING)
    private City city;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany
    private List<Repertoire> repertoires;

    @OneToMany
    private List<Screen> screens;
}
