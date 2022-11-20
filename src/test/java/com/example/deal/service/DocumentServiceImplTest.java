package com.example.deal.service;

import com.example.deal.repository.ApplicationRepository;
import com.example.deal.test_data.ApplicationTestData;
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
class DocumentServiceImplTest {

    @MockBean
    ApplicationServiceImpl applicationService;
    @MockBean
    ApplicationRepository applicationRepository;

    @Autowired
    DocumentServiceImpl documentService;

    @Test
    void getInfoForDocument() throws IOException {
        when(applicationService.getApplication(1L)).thenReturn(ApplicationTestData.getApplicationTestData());
        assertNotNull(documentService.getInfoForDocument(1L));
        verify(applicationService, times(1)).getApplication(any());

    }
}