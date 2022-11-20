package com.example.deal.test_data;

import com.example.deal.entity.Application;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ApplicationTestData {

    static String jsonPathApplicationDTO = "./src/test/resources/json/Application.json";
    static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public static List<Application> getAllApplicationData() throws IOException {
        return objectMapper.readValue(new File(jsonPathApplicationDTO), new TypeReference<>() {});
    }

    public static Application getApplicationTestData() throws IOException {
        return getAllApplicationData().get(0);
    }
}
