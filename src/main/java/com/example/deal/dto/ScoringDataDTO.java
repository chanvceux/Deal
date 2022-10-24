package com.example.deal.dto;

import com.example.deal.enumeration.Gender;
import com.example.deal.enumeration.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class ScoringDataDTO {
    BigDecimal amount;
    Integer term;
    String firstName;
    String lastName;
    String middleName;
    Gender gender;
    LocalDate birthdate;
    String passportSeries;
    String passportNumber;
    LocalDate passportIssueDate;
    String passportIssueBranch;
    MaritalStatus maritalStatus;
    Integer dependentAmount;
    EmploymentDTO employment;
    String account;
    Boolean isInsuranceEnabled;
    Boolean isSalaryClient;

}
