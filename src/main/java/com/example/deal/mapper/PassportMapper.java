package com.example.deal.mapper;

import com.example.deal.dto.LoanApplicationRequestDTO;
import com.example.deal.entity.Passport;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class PassportMapper {

    private PassportMapper() {
    }

    public static Passport passportBuilder(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        Passport passport = Passport.builder()
                .number(loanApplicationRequestDTO.getPassportNumber())
                .series(loanApplicationRequestDTO.getPassportSeries())
                .build();

        log.debug("RETURNING Passport, VALUE: {}", passport);
        return passport;
    }
}
