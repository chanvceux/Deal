package com.example.deal.service;

import com.example.deal.dto.*;
import com.example.deal.entity.Application;
import com.example.deal.entity.PaymentSchedule;

import java.util.List;

public interface ApplicationService {
    Long addApplication(LoanApplicationRequestDTO loanApplicationRequestDTO);
    void saveApplicationOffer(LoanOfferDTO loanOfferDTO);
    Application getApplication(Long applicationId);
    ApplicationDTO getApplicationDTO(Long applicationID);
    Application updateApplicationStatusHistory(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequestDTO);
    void finalUpdateApplication(Long applicationId, CreditDTO creditDTO, List<PaymentSchedule> paymentSchedules);

}
