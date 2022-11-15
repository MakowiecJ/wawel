package com.mako.wawel.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MOVIES")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class MovieEntity {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "GENRE")
    private String genre;

    @Column(name = "MIN_AGE")
    private int minAge;

    @Column(name = "DURATION")
    private int duration;

    @Column(name = "DESCRIPTION")
    private String description;
}
