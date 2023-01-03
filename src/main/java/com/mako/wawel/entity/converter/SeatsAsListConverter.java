package com.mako.wawel.entity.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mako.wawel.entity.cinema.Seat;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class SeatsAsListConverter implements AttributeConverter<List<Seat>, String> {

    @Override
    public String convertToDatabaseColumn(List<Seat> seats) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(seats);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public List<Seat> convertToEntityAttribute(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<Seat> seats = null;
        try {
            seats = mapper.readValue(json, new TypeReference<List<Seat>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return seats;
    }
}
