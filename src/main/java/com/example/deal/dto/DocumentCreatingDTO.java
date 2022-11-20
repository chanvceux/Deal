package com.example.deal.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentCreatingDTO {

    private String fullName;
    private LocalDate birthdate;
    private String gender;
    private String fullPassportData;
    private String email;
    private String martialStatus;
    private Integer dependentAmount;
    private EmploymentDTO employment;
    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;
    private List<PaymentScheduleElementDTO> paymentScheduleElementList;
    private Integer sesCode;

}
