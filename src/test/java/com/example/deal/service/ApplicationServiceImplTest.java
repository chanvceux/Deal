package com.example.deal.service;

import com.example.deal.entity.Application;
import com.example.deal.entity.ApplicationStatusHistory;
import com.example.deal.repository.ApplicationRepository;
import com.example.deal.repository.ApplicationStatusHistoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ApplicationServiceImplTest {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    ApplicationStatusHistoryRepository applicationStatusHistory;

    @Value("${json.application}")
    String path;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void successfullyAddedApplication() throws JsonProcessingException { //todo

        //given
        //when
        //then

    }
}
