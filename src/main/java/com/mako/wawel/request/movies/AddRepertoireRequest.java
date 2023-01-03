package com.mako.wawel.request.movies;

import com.mako.wawel.common.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
public class AddRepertoireRequest {
    private City city;
    private LocalDate startDate;
    private LocalDate endDate;
}