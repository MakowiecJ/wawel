package com.mako.wawel.response.movies;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepertoireItem {
    GeneralMovieResponse movie;
    List<ScreeningItem> screenings;
}
