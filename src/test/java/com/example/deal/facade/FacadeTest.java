package com.example.deal.facade;

import com.example.deal.enumeration.Theme;
import com.example.deal.feign_client.ConveyorMC;
import com.example.deal.repository.ApplicationRepository;
import com.example.deal.service.ApplicationServiceImpl;
import com.example.deal.service.DealServiceImpl;
import com.example.deal.service.KafkaProducerServiceImpl;
import com.example.deal.test_data.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FacadeTest {

    @MockBean
    ApplicationServiceImpl applicationService;
    @MockBean
    ApplicationRepository applicationRepository;

    @MockBean
    KafkaProducerServiceImpl kafkaProducerService;

    @MockBean
    DealServiceImpl dealService;

    @MockBean
    ConveyorMC conveyorMC;

    @Autowired
    Facade facade;

    @Test
    void offersDealFacade() throws IOException {
        when(applicationService.addApplication(LoanApplicationRequestTestData.getCorrectData())).thenReturn(1L);
        facade.offersDealFacade(LoanApplicationRequestTestData.getCorrectData());
        verify(applicationService, times(1)).addApplication(any());
    }

    @Test
    void calculateFacade() throws IOException {
        when(applicationService.updateApplicationStatusHistory(1L, FinishRegistrationTestData.getFinishRegistrationTestData()))
                .thenReturn(ApplicationTestData.getApplicationTestData());
        when(dealService.calculation(any())).thenReturn(CreditTestData.getFirstCorrectData());
        facade.calculateFacade(FinishRegistrationTestData.getFinishRegistrationTestData(), 1L);
        assertNotNull(facade.calculateFacade(FinishRegistrationTestData.getFinishRegistrationTestData(), 1L));

    }

    @Test
    void documentCodeFacadeEquals() throws JsonProcessingException {
        facade.documentCodeFacade(kafkaProducerService, 1l, 2345, 2345);
        verify(kafkaProducerService, times(1)).send("credit-issued", Theme.CREDIT_ISSUED, 1l);
    }

    @Test
    void documentCodeFacadeNotEquals() throws JsonProcessingException {
        facade.documentCodeFacade(kafkaProducerService, 1l, 2345, 1234);
        verify(kafkaProducerService, times(1)).send("application-denied", Theme.APPLICATION_DENIED, 1l);
    }
}