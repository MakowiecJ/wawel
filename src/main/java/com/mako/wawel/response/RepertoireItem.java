package com.mako.wawel.response;

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
