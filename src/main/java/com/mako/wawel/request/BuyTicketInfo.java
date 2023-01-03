package com.mako.wawel.request;

import com.mako.wawel.common.TicketType;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyTicketInfo {
    private TicketType ticketType;
    private int seatRow;
    private int seatNumber;
}
