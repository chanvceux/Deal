package com.example.deal.service;
import com.example.deal.dto.*;
import com.example.deal.entity.*;
import com.example.deal.enumeration.ApplicationStatus;
import com.example.deal.enumeration.CreditStatus;
import com.example.deal.repository.ApplicationRepository;
import com.example.deal.repository.ApplicationStatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    ClientServiceImpl clientService;
    @Autowired
    ApplicationStatusHistoryRepository applicationStatusHistoryRepository;

    public Long addApplication(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        Application application = Application.builder()
                .client(clientService.clientBuilder(loanApplicationRequestDTO))
                .applicationStatus(ApplicationStatus.PREAPPROVAL)
                .creation_date(LocalDate.now())
                .statusHistory(List.of(applicationStatusHistoryBuilder(ApplicationStatus.PREAPPROVAL)))
                .build();

        applicationRepository.save(application);
        return application.getId();
    }

    private Credit creditBuilder(LoanOfferDTO loanOfferDTO) {

        return Credit.builder()
                .amount(loanOfferDTO.getRequestedAmount())
                .term(loanOfferDTO.getTerm())
                .monthlyPayment(loanOfferDTO.getMonthlyPayment())
                .rate(loanOfferDTO.getRate())
                .psk(loanOfferDTO.getTotalAmount())
                .optionalServices(optionalServicesBuilder(loanOfferDTO))
                .creditStatus(CreditStatus.CALCULATED)
                .build();
    }

    private OptionalServices optionalServicesBuilder(LoanOfferDTO loanOfferDTO) {

        return OptionalServices.builder()
                .isInsuranceEnabled(loanOfferDTO.getIsInsuranceEnabled())
                .isSalaryClient(loanOfferDTO.getIsSalaryClient())
                .build();
    }

    public void saveApplicationOffer(LoanOfferDTO loanOfferDTO) {

        Application application = getApplication(loanOfferDTO.getApplicationId());
        application.setCredit(creditBuilder(loanOfferDTO));
        application.setSignDate(LocalDate.now());
        application.setAppliedOffer(loanOfferDTO.toString());
        application.setApplicationStatus(ApplicationStatus.CALCULATED);
        updateApplicationStatusHistory(application, ApplicationStatus.CALCULATED);
        applicationRepository.save(application);
    }

    public Application getApplication(Long applicationId) {

        return applicationRepository.findById(applicationId).orElseThrow(() ->
                new NoSuchElementException("Cannot find an application with this ID"));
    }

    private Employment employmentBuilder(FinishRegistrationRequestDTO finishRegistrationRequestDTO) {

        return Employment.builder()
                .employmentStatus(finishRegistrationRequestDTO.getEmployment().getEmploymentStatus())
                .employer(finishRegistrationRequestDTO.getEmployment().getEmployerINN())
                .salary(finishRegistrationRequestDTO.getEmployment().getSalary())
                .position(finishRegistrationRequestDTO.getEmployment().getPosition())
                .work_experience_total(finishRegistrationRequestDTO.getEmployment().getWorkExperienceTotal())
                .work_experience_current(finishRegistrationRequestDTO.getEmployment().getWorkExperienceCurrent())
                .build();
    }

    public Application updateApplicationStatusHistory(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequestDTO) {

        Application application = getApplication(applicationId);
        application.getClient().setGender(finishRegistrationRequestDTO.getGender());
        application.getClient().getPassport().setIssue_date(finishRegistrationRequestDTO.getPassportIssueDate());
        application.getClient().getPassport().setIssue_branch(finishRegistrationRequestDTO.getPassportIssueBranch());
        application.getClient().setMaritalStatus(finishRegistrationRequestDTO.getMaritalStatus());
        application.getClient().setDependentAmount(finishRegistrationRequestDTO.getDependentAmount());
        application.getClient().setEmployment(employmentBuilder(finishRegistrationRequestDTO));
        application.getClient().setAccount(finishRegistrationRequestDTO.getAccount());
        applicationRepository.save(application);

        return application;
    }

    public ScoringDataDTO scoringDataDTOBuilder(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequestDTO) {

        Application application = updateApplicationStatusHistory(applicationId, finishRegistrationRequestDTO);
        return ScoringDataDTO.builder()
                .amount(application.getCredit().getAmount())
                .term(application.getCredit().getTerm())
                .firstName(application.getClient().getFirst_name())
                .middleName(application.getClient().getMiddle_name())
                .lastName(application.getClient().getLast_name())
                .gender(application.getClient().getGender())
                .birthdate(application.getClient().getBirthdate())
                .passportSeries(application.getClient().getPassport().getSeries())
                .passportNumber(application.getClient().getPassport().getNumber())
                .passportIssueBranch(application.getClient().getPassport().getIssue_branch())
                .passportIssueDate(application.getClient().getPassport().getIssue_date())
                .maritalStatus(application.getClient().getMaritalStatus())
                .dependentAmount(application.getClient().getDependentAmount())
                .employment(finishRegistrationRequestDTO.getEmployment())
                .account(application.getClient().getAccount())
                .isInsuranceEnabled(application.getCredit().getOptionalServices().getIsInsuranceEnabled())
                .isSalaryClient(application.getCredit().getOptionalServices().getIsSalaryClient())
                .build();
    }

    public void finalUpdateApplication(Long applicationId, CreditDTO creditDTO, List<PaymentSchedule> paymentSchedules) {

        Application application = getApplication(applicationId);
        application.setApplicationStatus(ApplicationStatus.APPROVED);
        application.getCredit().setAmount(creditDTO.getAmount());
        application.getCredit().setTerm(creditDTO.getTerm());
        application.getCredit().setMonthlyPayment(creditDTO.getMonthlyPayment());
        application.getCredit().setRate(creditDTO.getRate());
        application.getCredit().setPsk(creditDTO.getPsk());
        application.getCredit().getOptionalServices().setIsInsuranceEnabled(creditDTO.getIsInsuranceEnabled());
        application.getCredit().getOptionalServices().setIsSalaryClient(creditDTO.getIsSalaryClient());
        application.getCredit().setPaymentSchedule(paymentSchedules);
        updateApplicationStatusHistory(application, ApplicationStatus.APPROVED);
    }

    @Transactional
    public void updateApplicationStatusHistory(Application application, ApplicationStatus applicationStatus) {

        List<ApplicationStatusHistory> list = new ArrayList<>();
        list.add(applicationStatusHistoryBuilder(applicationStatus));
        application.getStatusHistory().add(list.get(0));
        applicationRepository.save(application);
    }

    private ApplicationStatusHistory applicationStatusHistoryBuilder(ApplicationStatus applicationStatus) {

        return applicationStatusHistoryRepository.save(ApplicationStatusHistory.builder()
                .status(applicationStatus)
                .time(LocalDateTime.now())
                .build());
    }

}
