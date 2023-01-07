package com.mako.wawel.response;

import com.mako.wawel.common.City;
import com.mako.wawel.common.ScreenName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketsByScreeningResponse {
    private Long screeningId;
    private City city;
    private LocalDate date;
    private LocalTime startTime;
    private String movieTitle;
    private Long movieId;
    private ScreenName screenName;
    private List<TicketResponse> tickets;

    public void addTicket(final TicketResponse ticket) {
        this.tickets.add(ticket);
    }

    public TicketsByScreeningResponse(final TicketsByScreeningResponse response) {
        this.screeningId = response.screeningId;
        this.city = response.city;
        this.date = response.date;
        this.startTime = response.startTime;
        this.movieTitle = response.movieTitle;
        this.movieId = response.movieId;
        this.screenName = response.screenName;
        this.tickets = new ArrayList<>(response.tickets);
    }
}
