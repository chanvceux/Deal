package com.example.deal.mapper;

import com.example.deal.dto.EmailMessageDTO;
import com.example.deal.entity.Application;
import com.example.deal.enumeration.Theme;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class EmailMapper {

    private EmailMapper() {
    }

    public static EmailMessageDTO emailBuilder(Application application, Theme theme) {

        EmailMessageDTO emailMessageDTO = EmailMessageDTO.builder()
                .address(application.getClient().getEmail())
                .theme(theme)
                .applicationID(application.getId())
                .build();

        log.trace("CREATED emailMessageDTO, VALUE: {}", emailMessageDTO);
        return emailMessageDTO;
    }
}
