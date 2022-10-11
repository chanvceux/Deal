package com.example.deal.service;

import com.example.deal.dto.EmailMessageDTO;
import com.example.deal.entity.Application;
import com.example.deal.enumeration.Theme;
import com.example.deal.mapper.EmailMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final ApplicationServiceImpl applicationService;

    public void send(String topic, Theme theme, Long id) throws JsonProcessingException {
        Application application = applicationService.getApplication(id);
        EmailMessageDTO emailMessage = EmailMapper.emailBuilder(application, theme);

        String json = objectMapper.writeValueAsString(emailMessage);
        log.info("CREATED emailMessage, VALUE: {}", json);

        kafkaTemplate.send(topic, json);
        log.info("SEND MESSAGE FOR: {}", topic);
    }
}