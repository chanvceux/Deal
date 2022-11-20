package com.example.deal.test_data;

import com.example.deal.dto.LoanOfferDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
public class LoanOfferTestData {
    static String jsonPathLoanOfferDTO = "./src/test/resources/json/LoanOffer.json";
    static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public static List<LoanOfferDTO> getLoanOfferTestData() throws IOException {
        System.out.println(jsonPathLoanOfferDTO);
        return objectMapper.readValue(new File(jsonPathLoanOfferDTO), new TypeReference<>() {});
    }

}
