package com.mako.wawel.request;

import com.mako.wawel.common.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class EditRepertoireRequest {
    private City city;
    private LocalDate date;
    List<EditScreeningRequest> screenings;
}
