package com.example.deal.test_data;

import com.example.deal.dto.FinishRegistrationRequestDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FinishRegistrationTestData {

    static String jsonPathFinishRegistrationDTO = "./src/test/resources/json/FinishRegistrationRequest.json";
    static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public static FinishRegistrationRequestDTO getFinishRegistrationTestData() throws IOException {
        return objectMapper.readValue(new File(jsonPathFinishRegistrationDTO), new TypeReference<>() {});
    }


}
