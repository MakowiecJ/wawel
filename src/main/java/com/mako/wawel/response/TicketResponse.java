package com.mako.wawel.response;

import com.mako.wawel.common.City;
import com.mako.wawel.common.ScreenName;
import com.mako.wawel.common.TicketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private Long id;
    private City city;
    private LocalDate date;
    private LocalTime startTime;
    private String movieTitle;
    private ScreenName screenName;
    private TicketType ticketType;
    private int seatRow;
    private int seatNumber;
}
