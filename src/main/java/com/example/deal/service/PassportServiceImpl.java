package com.example.deal.service;

import com.example.deal.dto.LoanApplicationRequestDTO;
import com.example.deal.entity.Passport;
import org.springframework.stereotype.Service;
@Service
public class PassportServiceImpl implements PassportService {

    public Passport passportBuilder(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        return  Passport.builder()
                .number(loanApplicationRequestDTO.getPassportNumber())
                .series(loanApplicationRequestDTO.getPassportSeries())
                .build();
    }
}
