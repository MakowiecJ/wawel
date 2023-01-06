package com.mako.wawel.request;

import com.mako.wawel.common.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor(staticName = "of")
@Builder
public class AddRepertoireRequest {
    private City city;
    private LocalDate date;
}
