package com.devt.blogger.utils.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Json {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    private final Object object;

    private Json(Object object) {
        this.object = object;
    }

    public static Json withObject(Object object) {
        return new Json(object);
    }

    public static <T> T objectify(Object object, Class<T> clazz) {
        return MAPPER.convertValue(object, clazz);
    }

    public String stringify() {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

}