package com.mako.wawel.request;

import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyTicketsRequest {
    private Long userId;
    private Long screeningId;
    List<BuyTicketInfo> tickets;
}
