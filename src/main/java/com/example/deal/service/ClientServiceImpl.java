package com.example.deal.service;

import com.example.deal.dto.LoanApplicationRequestDTO;
import com.example.deal.entity.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {
    private final PassportServiceImpl passportService;
    @Autowired
    public ClientServiceImpl(PassportServiceImpl passportService) {
        this.passportService = passportService;
    }

    public Client clientBuilder(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        Client client = Client.builder()
                .firstName(loanApplicationRequestDTO.getFirstName())
                .middleName(loanApplicationRequestDTO.getMiddleName())
                .lastName(loanApplicationRequestDTO.getLastName())
                .email(loanApplicationRequestDTO.getEmail())
                .birthdate(loanApplicationRequestDTO.getBirthdate())
                .passport(passportService.passportBuilder(loanApplicationRequestDTO))
                .build();

        log.debug("CREATING and RETURNING Client, VALUE: {}", client);
        return client;
    }

}
