package com.example.deal.dto;

import com.example.deal.enumeration.Gender;
import com.example.deal.enumeration.MaritalStatus;

import java.time.LocalDate;

public class FinishRegistrationRequestDTO {

    Gender gender;
    MaritalStatus maritalStatus;
    Integer dependentAmount;
    LocalDate passportIssueDate;
    String passportIssueBrach;
    EmploymentDTO employment;
    String account;

}
