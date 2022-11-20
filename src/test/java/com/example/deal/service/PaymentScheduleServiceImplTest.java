package com.example.deal.service;
import com.example.deal.repository.PaymentScheduleRepository;
import com.example.deal.test_data.PaymentScheduleTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PaymentScheduleServiceImplTest {

    @Autowired
    PaymentScheduleServiceImpl paymentScheduleService;

    @MockBean
    PaymentScheduleRepository paymentScheduleRepository;
    @Test
    public void addPaymentScheduleRepository() throws IOException {
        paymentScheduleService.addPaymentScheduleRepository(PaymentScheduleTestData.getPaymentScheduleTestData().get(0));
        verify(paymentScheduleRepository, times(1)).save(PaymentScheduleTestData.getPaymentScheduleTestData().get(0));
    }

}
