package com.example.deal.service;

import com.example.deal.entity.Application;
import com.example.deal.feign_client.ConveyorMC;
import com.example.deal.repository.ApplicationRepository;
import com.example.deal.test_data.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DealServiceImplTest {

    @MockBean
    Application application;

    @Autowired
    @InjectMocks
    ApplicationServiceImpl applicationService;

    @MockBean
    ApplicationRepository applicationRepository;

    @Autowired
    @InjectMocks
    DealServiceImpl dealService;

    @MockBean
    ConveyorMC conveyorMC;
    @Test
    public void offers() throws IOException {
        when(conveyorMC.offers(LoanApplicationRequestTestData.getCorrectData())).thenReturn(LoanOfferTestData.getLoanOfferTestData());
        assertEquals(dealService.offers(LoanApplicationRequestTestData.getCorrectData()), LoanOfferTestData.getLoanOfferTestData());
    }

    @Test
    public void offersWrongData() throws IOException {
        when(conveyorMC.offers(LoanApplicationRequestTestData.getDataWithWrongFirstName())).thenThrow(new RuntimeException("Wrong firstName"));
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            dealService.offers(LoanApplicationRequestTestData.getDataWithWrongFirstName());
        });
        assertEquals("Wrong firstName", thrown.getMessage());
    }

    @Test
    public void calculation() throws IOException {
        when(conveyorMC.calculation(ScoringTestData.getCorrectDataFirst())).thenReturn(CreditTestData.getFirstCorrectData());
        dealService.calculation(ScoringTestData.getCorrectDataFirst());
        verify(conveyorMC, times(1)).calculation(any());
    }

    @Test
    public void calculationRejectedByStatus() throws IOException {
        when(conveyorMC.calculation(ScoringTestData.getRejectedByUnemployedStatus())).thenThrow(new RuntimeException("rejected by unemployed status"));
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> dealService.calculation(ScoringTestData.getRejectedByUnemployedStatus()));
        verify(conveyorMC, times(1)).calculation(any());
        assertEquals("rejected by unemployed status", thrown.getMessage());
    }

    @Test
    public void calculationRejectedByAge() throws IOException {
        when(conveyorMC.calculation(ScoringTestData.getRejectedByAge())).thenThrow(new RuntimeException("rejected by age"));
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> dealService.calculation(ScoringTestData.getRejectedByAge()));
        verify(conveyorMC, times(1)).calculation(any());
        assertEquals("rejected by age", thrown.getMessage());
    }
}
