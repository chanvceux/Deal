package com.example.deal.service;

import com.example.deal.enumeration.Theme;
import com.example.deal.test_data.ApplicationTestData;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class KafkaProducerServiceImplTest {

    @Autowired
    @InjectMocks
    KafkaProducerServiceImpl kafkaProducerService;

    @MockBean
    ApplicationServiceImpl applicationService;

    @Test
    void send() throws IOException {
        when(applicationService.getApplication(1L)).thenReturn(ApplicationTestData.getApplicationTestData());
        kafkaProducerService.send("credit-issued", Theme.CREDIT_ISSUED, 1l);
        verify(applicationService, times(1)).getApplication(1L);

    }
}