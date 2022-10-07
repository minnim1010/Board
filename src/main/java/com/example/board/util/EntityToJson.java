package com.example.board.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class EntityToJson<T> {
    private static ObjectMapper objectMapper;

    public EntityToJson() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public String convertEntityToJsonString(T entity) throws JsonProcessingException {
        return objectMapper.writeValueAsString(entity);
    }
}
