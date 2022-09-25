package com.example.deal.service;
import com.example.deal.dto.CreditDTO;
import com.example.deal.dto.ScoringDataDTO;
import com.example.deal.entity.PaymentSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final ApplicationServiceImpl applicationServiceImpl;
    private final PaymentScheduleServiceImpl paymentScheduleImpl;

    public void updateCredit(CreditDTO creditDTO, ScoringDataDTO scoringDataDTO, Long applicationId) {

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

        applicationServiceImpl.finalUpdateApplication(applicationId, creditDTO, paymentSchedules);
    }
}
