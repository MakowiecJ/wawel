package com.mako.wawel.response;

import com.mako.wawel.common.City;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@ToString
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class GetRepertoireResponse {
    private City city;
    private LocalDate date;
    private List<RepertoireItem> items;
}
