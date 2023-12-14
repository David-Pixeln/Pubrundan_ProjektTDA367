package com.Pubrunda;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.util.List;

public class JsonObjectMapper {

    private static final ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();


    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getObjectFromJson(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

    public static <T> List<T> getObjectListFromJson(String json, Class<T> clazz) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        return objectMapper.readValue(
                rootNode.traverse(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)
        );
    }

}
