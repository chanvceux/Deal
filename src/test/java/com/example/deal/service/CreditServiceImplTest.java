package com.example.deal.service;

import com.example.deal.test_data.CreditTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CreditServiceImplTest {

    @Autowired
    CreditServiceImpl creditService;

    @MockBean
    ApplicationServiceImpl applicationService;

    @Test
    public void updateCredit() throws IOException {
        creditService.updateCredit(CreditTestData.getFirstCorrectData(), 1l);
        verify(applicationService, times(1)).finalUpdateApplication(any(), any(), any());
    }

    @Test
    public void updateCreditSecondData() throws IOException {
        creditService.updateCredit(CreditTestData.getSecondCorrectData(), 1l);
        verify(applicationService, times(1)).finalUpdateApplication(any(), any(), any());
    }
}
