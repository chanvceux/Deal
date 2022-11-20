package com.example.deal.controller;

import com.example.deal.entity.Application;
import com.example.deal.facade.Facade;
import com.example.deal.feign_client.ConveyorMC;
import com.example.deal.repository.ApplicationRepository;
import com.example.deal.service.ApplicationServiceImpl;
import com.example.deal.service.CreditServiceImpl;
import com.example.deal.service.KafkaProducerServiceImpl;
import com.example.deal.test_data.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DealControllerTest {

    @MockBean
    ApplicationServiceImpl applicationService;

    private ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    @Autowired
    MockMvc mockMvc;
    @MockBean
     Facade facade;

    @MockBean
    Application application;

    @MockBean
    ConveyorMC conveyorMC;
    @MockBean
    ApplicationRepository applicationRepository;

    @MockBean
    CreditServiceImpl creditService;

    @MockBean
    KafkaProducerServiceImpl kafkaProducerService;

    @Test
    void correctOffersDeal() throws Exception {

        when(conveyorMC.offers(LoanApplicationRequestTestData.getCorrectData())).thenReturn(LoanOfferTestData.getLoanOfferTestData());
        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(LoanApplicationRequestTestData.getCorrectData())))
                .andExpect(status().isOk());

        verify(facade, times(1)).offersDealFacade(any());
    }

    @Test
    void addOffer() throws Exception {

        when(applicationRepository.findById(1l)).thenReturn(Optional.ofNullable(ApplicationTestData.getApplicationTestData()));
        mockMvc.perform(put("/deal/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(LoanOfferTestData.getLoanOfferTestData().get(0))))
                .andExpect(status().isOk());
    }

    @Test
    void calculate() throws Exception {

        when(applicationService.getApplication(1L)).thenReturn(ApplicationTestData.getApplicationTestData());
        mockMvc.perform(put("/deal/calculate/{applicationId}", 1l)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(FinishRegistrationTestData.getFinishRegistrationTestData())))
                .andExpect(status().isOk());

        verify(creditService, times(1)).updateCredit(any(), any());
    }


//    @Test
//    void documentCode() throws Exception {
//        when(applicationService.getApplication(1L)).thenReturn(ApplicationTestData.getApplicationTestData());
//        mockMvc.perform(post("/deal/document/{applicationId}/code", 1l).content("8392"))
//                .andExpect(status().isOk());
//    }

    @Test
    void documentSign() throws Exception {
        mockMvc.perform(post("/deal/document/{applicationId}/sign", 1l))
                .andExpect(status().isOk());
    }


    @Test
    void documentSend() throws Exception {
        mockMvc.perform(post("/deal/document/{applicationId}/send", 1l))
                .andExpect(status().isOk());
    }


    @Test
    void getApplication() throws Exception {
        when(applicationService.getApplication(1L)).thenReturn(ApplicationTestData.getApplicationTestData());
        mockMvc.perform(post("/deal/application/{applicationId}", 1l))
                .andExpect(status().isOk());
    }

    @Test
    void getApplicationById() throws Exception {
        when(applicationService.getApplication(1L)).thenReturn(ApplicationTestData.getApplicationTestData());
        mockMvc.perform(get("/deal/admin/application/{applicationId}", 1l)).andExpect(status().isOk());
    }

}
