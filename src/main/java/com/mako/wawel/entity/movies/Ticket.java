package com.mako.wawel.entity.movies;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Ticket {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    private Screening screening;

    private int seatNumber;
    private double price;
}
