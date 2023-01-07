package com.mako.wawel.response;

import com.mako.wawel.entity.auth.Role;
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
    private Set<Role> roles;
    private List<TicketsByScreeningResponse> tickets;
    private List<GeneralMovieResponse> watchedMovies;
    private List<MovieReviewResponse> reviews;
}
