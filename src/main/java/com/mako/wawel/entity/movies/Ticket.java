package com.mako.wawel.entity.movies;

import com.mako.wawel.common.TicketType;
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

    @Column(name = "seat_row")
    private int seatRow;

    @Column(name = "seat_number")
    private int seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type")
    private TicketType ticketType;


}
