package com.setu.bank.utils;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.setu.bank.models.entities.RestrictionAction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonConverter implements AttributeConverter<RestrictionAction, String> {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public String convertToDatabaseColumn(RestrictionAction jsonData) {
        try {
            return objectMapper.writeValueAsString(jsonData);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public RestrictionAction convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, RestrictionAction.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
