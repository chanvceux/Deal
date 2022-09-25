package com.example.deal.service;

import com.example.deal.dto.LoanApplicationRequestDTO;
import com.example.deal.entity.Passport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class PassportServiceImpl implements PassportService {

    public Passport passportBuilder(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        Passport passport = Passport.builder()
                .number(loanApplicationRequestDTO.getPassportNumber())
                .series(loanApplicationRequestDTO.getPassportSeries())
                .build();

        log.debug("RETURNING Passport, VALUE: {}", passport);
        return passport;
    }
}
