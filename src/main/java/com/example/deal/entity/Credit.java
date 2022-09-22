package com.example.deal.entity;

import com.example.deal.enumeration.CreditStatus;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    private CreditStatus creditStatus;

//    Boolean isInsuranceEnabled; // todo
//    Boolean isSalaryClient;
//    List<PaymentScheduleElementDTO> paymentSchedule;

}
