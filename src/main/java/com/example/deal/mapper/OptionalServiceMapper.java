package com.example.deal.mapper;

import com.example.deal.dto.LoanOfferDTO;
import com.example.deal.entity.OptionalServices;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OptionalServiceMapper {
    private OptionalServiceMapper() {
    }

    public static OptionalServices optionalServicesBuilder(LoanOfferDTO loanOfferDTO) {

        log.debug("GETTING loanOfferDTO, INPUT VALUES: {}", loanOfferDTO);
        OptionalServices optionalServices = OptionalServices.builder()
                .isInsuranceEnabled(loanOfferDTO.getIsInsuranceEnabled())
                .isSalaryClient(loanOfferDTO.getIsSalaryClient())
                .build();
        log.debug("RETURNING OptionalServices, VALUE: {}", optionalServices);

        return optionalServices;
    }
}
