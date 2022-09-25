package com.example.deal.service;
import com.example.deal.dto.CreditDTO;
import com.example.deal.dto.ScoringDataDTO;
import com.example.deal.entity.PaymentSchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final ApplicationServiceImpl applicationServiceImpl;
    private final PaymentScheduleServiceImpl paymentScheduleImpl;

    public void updateCredit(CreditDTO creditDTO, Long applicationId) {

        List<PaymentSchedule> paymentSchedules = new ArrayList<>();

        for (int i = 0; i < creditDTO.getPaymentSchedule().size(); i++) {
            PaymentSchedule paymentSchedule = PaymentSchedule.builder()
                    .number(creditDTO.getPaymentSchedule().get(i).getNumber())
                    .date(creditDTO.getPaymentSchedule().get(i).getDate())
                    .totalPayment(creditDTO.getPaymentSchedule().get(i).getTotalPayment())
                    .interestPayment(creditDTO.getPaymentSchedule().get(i).getInterestPayment())
                    .debtPayment(creditDTO.getPaymentSchedule().get(i).getDebtPayment())
                    .remainingDebt(creditDTO.getPaymentSchedule().get(i).getRemainingDebt())
                    .build();

            paymentSchedules.add(paymentScheduleImpl.addPaymentScheduleRepository(paymentSchedule));
        }

        log.debug("Creating List<PaymentSchedule>, VALUES: {}", paymentSchedules);
        applicationServiceImpl.finalUpdateApplication(applicationId, creditDTO, paymentSchedules);
    }
}
