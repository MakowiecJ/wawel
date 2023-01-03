package com.mako.wawel.entity.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SeatsConverter implements AttributeConverter<String[][], String> {
    @Override
    public String convertToDatabaseColumn(String[][] seats) {
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
    public String[][] convertToEntityAttribute(String json) {
        ObjectMapper mapper = new ObjectMapper();
        String[][] seats = null;
        try {
            seats = mapper.readValue(json, new TypeReference<String[][]>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return seats;
    }
}
