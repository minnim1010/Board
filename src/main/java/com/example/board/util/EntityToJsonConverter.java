package com.example.board.util;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class EntityToJsonConverter<T> {
    private static ObjectMapper objectMapper;
    private String timeStampPattern = "yyyy-MM-dd HH:mm";
    SimpleDateFormat simpleDateFormat;

    public EntityToJsonConverter() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        simpleDateFormat = new SimpleDateFormat(timeStampPattern);
        objectMapper.setDateFormat(simpleDateFormat);
    }

    public String convertEntityToJsonString(T entity) throws JsonProcessingException {
        return objectMapper.writeValueAsString(entity);
    }
}
