package com.example.deal.service;

import com.example.deal.entity.Application;
import com.example.deal.repository.ApplicationRepository;
import com.example.deal.test_data.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ApplicationServiceImplTest {

    @MockBean
    Application application;

    @Autowired
    @InjectMocks
    ApplicationServiceImpl applicationService;

    @MockBean
    ApplicationRepository applicationRepository;

    @Test
    public void saveApplicationOfferTest() throws IOException {
        when(applicationRepository.findById(1l)).thenReturn(Optional.ofNullable(ApplicationTestData.getApplicationTestData()));
        applicationService.saveApplicationOffer(LoanOfferTestData.getLoanOfferTestData().get(0));
        verify(applicationRepository, times(1)).findById(any());
    }

    @Test
    public void saveApplicationOfferTestWithException() throws IOException {
        when(applicationRepository.findById(2l)).thenThrow(NoSuchElementException.class);

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            applicationService.saveApplicationOffer(LoanOfferTestData.getLoanOfferTestData().get(0));
        });
        assertEquals(thrown.getMessage(), "Cannot find an application with this ID");
        verify(applicationRepository, times(1)).findById(any());
    }
    @Test
    public void updateApplicationStatusHistory() throws IOException {
        when(applicationRepository.findById(1l)).thenReturn(Optional.ofNullable(ApplicationTestData.getApplicationTestData()));
        applicationService.updateApplicationStatusHistory(1L, FinishRegistrationTestData.getFinishRegistrationTestData());
        verify(applicationRepository, times(1)).findById(any());

    }
    @Test
    public void updateApplicationStatusHistoryWithException() throws IOException {
        when(applicationRepository.findById(2l)).thenThrow(NoSuchElementException.class);

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            applicationService.updateApplicationStatusHistory(1L, FinishRegistrationTestData.getFinishRegistrationTestData());
        });
        assertEquals(thrown.getMessage(), "Cannot find an application with this ID");
        verify(applicationRepository, times(1)).findById(any());
    }

    @Test
    public void finalUpdateApplication() throws IOException {
        when(applicationRepository.findById(1l)).thenReturn(Optional.ofNullable(ApplicationTestData.getApplicationTestData()));
        applicationService.finalUpdateApplication(1L, CreditTestData.getFirstCorrectData(), PaymentScheduleTestData.getPaymentScheduleTestData());
        verify(applicationRepository, times(1)).findById(any());
    }

    @Test
    public void getAllApplication() throws IOException {
        when(applicationRepository.findAll()).thenReturn(ApplicationTestData.getAllApplicationData());
        applicationService.getALlApplications();
        verify(applicationRepository, times(1)).findAll();
    }

}
