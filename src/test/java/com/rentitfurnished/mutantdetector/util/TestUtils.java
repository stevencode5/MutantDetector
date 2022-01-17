package com.rentitfurnished.mutantdetector.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

public class TestUtils {

    public static String asJsonString(final Object obj) {
        try {
            return objectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // configure date parsing/formatting
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return objectMapper;
    }

}
