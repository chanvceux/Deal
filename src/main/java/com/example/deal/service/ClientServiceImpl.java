package com.example.deal.service;

import com.example.deal.dto.LoanApplicationRequestDTO;
import com.example.deal.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    PassportServiceImpl passportService;

    public Client clientBuilder(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        return Client.builder()
                .first_name(loanApplicationRequestDTO.getFirstName())
                .middle_name(loanApplicationRequestDTO.getMiddleName())
                .last_name(loanApplicationRequestDTO.getLastName())
                .email(loanApplicationRequestDTO.getEmail())
                .birthdate(loanApplicationRequestDTO.getBirthdate())
                .passport(passportService.passportBuilder(loanApplicationRequestDTO))
                .build();
    }

}
