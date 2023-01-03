package com.mako.wawel.request;

import com.mako.wawel.response.TicketResponse;
import com.mako.wawel.response.MovieReviewResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class GetUserInfoResponse {
    private String username;
    private String email;
    private List<TicketResponse> tickets;
    private Set<Long> watchedMovies;
    private List<MovieReviewResponse> reviews;
}
