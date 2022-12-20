package com.mako.wawel.entity.movies;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Review {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "RATING")
    private Integer rating;

    @Column(name = "REVIEW")
    private String review;
}
