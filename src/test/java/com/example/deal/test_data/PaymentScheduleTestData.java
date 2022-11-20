package com.example.deal.test_data;

import com.example.deal.entity.PaymentSchedule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PaymentScheduleTestData {

    static String jsonPathPaymentScheduleDTO = "./src/test/resources/json/PaymentSchedule.json";
    static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public static List<PaymentSchedule> getPaymentScheduleTestData() throws IOException {
        return objectMapper.readValue(new File(jsonPathPaymentScheduleDTO), new TypeReference<>() {});
    }
}
