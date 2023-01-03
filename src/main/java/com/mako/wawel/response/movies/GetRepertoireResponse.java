package com.mako.wawel.response.movies;

import lombok.*;

import java.util.List;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetRepertoireResponse {
    private List<RepertoireItem> items;
}
