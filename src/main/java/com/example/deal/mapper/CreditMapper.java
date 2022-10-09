package com.example.deal.mapper;

import com.example.deal.dto.LoanOfferDTO;
import com.example.deal.entity.Credit;
import com.example.deal.enumeration.CreditStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreditMapper {
    private CreditMapper() {
    }

    public static Credit creditBuilder(LoanOfferDTO loanOfferDTO) {

        log.debug("GETTING loanOfferDTO, INPUT VALUES: {}", loanOfferDTO);

        Credit credit = Credit.builder()
                .amount(loanOfferDTO.getRequestedAmount())
                .term(loanOfferDTO.getTerm())
                .monthlyPayment(loanOfferDTO.getMonthlyPayment())
                .rate(loanOfferDTO.getRate())
                .psk(loanOfferDTO.getTotalAmount())
                .optionalServices(OptionalServiceMapper.optionalServicesBuilder(loanOfferDTO))
                .creditStatus(CreditStatus.CALCULATED)
                .build();
        log.debug("RETURNING Credit, VALUE: {}", credit);

        return credit;
    }
}
