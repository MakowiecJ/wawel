package com.mako.wawel.request;

import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyTicketsRequest {
    List<BuyTicketInfo> tickets;
}
