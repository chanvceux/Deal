package com.example.deal.mapper;

import com.example.deal.dto.*;
import com.example.deal.entity.Credit;
import com.example.deal.enumeration.CreditStatus;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CreditMapper {
    static ModelMapper modelMapper = new ModelMapper();


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

    public static CreditDTO creditToCreditDTO(Credit credit) {

        List<PaymentScheduleElementDTO> paymentScheduleElementDTOS = new ArrayList<>();

        for (int i = 0; i < credit.getPaymentSchedule().size(); i++) {
            paymentScheduleElementDTOS.add(modelMapper.map(credit.getPaymentSchedule().get(i),
                    PaymentScheduleElementDTO.class));
        }

        return CreditDTO.builder()
                .amount(credit.getAmount())
                .term(credit.getTerm())
                .monthlyPayment(credit.getMonthlyPayment())
                .rate(credit.getRate())
                .psk(credit.getPsk())
                .isInsuranceEnabled(credit.getOptionalServices().getIsInsuranceEnabled())
                .isSalaryClient(credit.getOptionalServices().getIsSalaryClient())
                .paymentSchedule(paymentScheduleElementDTOS)
                .build();
    }
}
