package com.example.deal.mapper;

import com.example.deal.dto.LoanApplicationRequestDTO;
import com.example.deal.entity.Client;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class ClientMapper {

    private ClientMapper() {
    }

    public static Client clientBuilder(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        Client client = Client.builder()
                .firstName(loanApplicationRequestDTO.getFirstName())
                .middleName(loanApplicationRequestDTO.getMiddleName())
                .lastName(loanApplicationRequestDTO.getLastName())
                .email(loanApplicationRequestDTO.getEmail())
                .birthdate(loanApplicationRequestDTO.getBirthdate())
                .passport(PassportMapper.passportBuilder(loanApplicationRequestDTO))
                .build();

        log.debug("CREATING and RETURNING Client, VALUE: {}", client);
        return client;
    }
}
