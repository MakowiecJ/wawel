package com.mako.wawel.response.movies;

import com.mako.wawel.entity.movies.Showing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class GetRepertoireResponse {
    private List<Showing> showings;
}
