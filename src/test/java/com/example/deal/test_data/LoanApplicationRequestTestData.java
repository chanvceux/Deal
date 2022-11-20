package com.example.deal.test_data;

import com.example.deal.dto.LoanApplicationRequestDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoanApplicationRequestTestData {

    static String jsonPathLoanAppReqDTO = "./src/test/resources/json/LoanApplicationRequest.json";
    static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    private static List<LoanApplicationRequestDTO> getLoanAppReqTestData() throws IOException {
        return objectMapper.readValue(new File(jsonPathLoanAppReqDTO), new TypeReference<>() {});
    }

    public static LoanApplicationRequestDTO getDataWithWrongFirstName() throws IOException {
        return getLoanAppReqTestData().get(0);
    }

    public static LoanApplicationRequestDTO getCorrectData() throws IOException {
        return getLoanAppReqTestData().get(3);
    }

}
