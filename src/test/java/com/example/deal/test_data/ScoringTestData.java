package com.example.deal.test_data;

import com.example.deal.dto.ScoringDataDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ScoringTestData {
    static String jsonPathScoringDataDTO = "./src/test/resources/json/ScoringData.json";
    static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    private static List<ScoringDataDTO> getScoringTestData() throws IOException {
         return objectMapper.readValue(new File(jsonPathScoringDataDTO), new TypeReference<>() {});
    }

    public static ScoringDataDTO getCorrectDataFirst() throws IOException {
        return getScoringTestData().get(0);
    }

    public static ScoringDataDTO getRejectedByAge() throws IOException {
        return getScoringTestData().get(5);
    }

    public static ScoringDataDTO getRejectedByUnemployedStatus() throws IOException {
        return getScoringTestData().get(6);
    }

}
