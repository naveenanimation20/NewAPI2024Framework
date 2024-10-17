package com.qa.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.io.IOException;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Generic method to deserialize JSON response into a specified class
    public static <T> T deserialize(Response response, Class<T> targetClass) {
        try {
            // Convert JSON response body to the desired class type
            return objectMapper.readValue(response.getBody().asString(), targetClass);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize response body to " + targetClass.getName(), e);
        }
    }
}
