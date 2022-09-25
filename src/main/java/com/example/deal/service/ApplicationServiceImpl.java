package com.example.deal.service;
import com.example.deal.dto.*;
import com.example.deal.entity.*;
import com.example.deal.enumeration.ApplicationStatus;
import com.example.deal.enumeration.CreditStatus;
import com.example.deal.repository.ApplicationRepository;
import com.example.deal.repository.ApplicationStatusHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    ClientServiceImpl clientService;
    @Autowired
    ApplicationStatusHistoryRepository applicationStatusHistoryRepository;

    public Long addApplication(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        log.debug("GETTING LoanApplicationRequestDTO, INPUT VALUES: {}", loanApplicationRequestDTO);

        Application application = Application.builder()
                .client(clientService.clientBuilder(loanApplicationRequestDTO))
                .applicationStatus(ApplicationStatus.PREAPPROVAL)
                .creation_date(LocalDate.now())
                .statusHistory(List.of(applicationStatusHistoryBuilder(ApplicationStatus.PREAPPROVAL)))
                .build();

        log.debug("BUILDING application, VALUE: {}", application);
        applicationRepository.save(application);

        log.debug("RETURNING application id, VALUE: {}", application.getId());
        return application.getId();
    }

    private Credit creditBuilder(LoanOfferDTO loanOfferDTO) {

        log.debug("GETTING loanOfferDTO, INPUT VALUES: {}", loanOfferDTO);
        Credit credit = Credit.builder()
                .amount(loanOfferDTO.getRequestedAmount())
                .term(loanOfferDTO.getTerm())
                .monthlyPayment(loanOfferDTO.getMonthlyPayment())
                .rate(loanOfferDTO.getRate())
                .psk(loanOfferDTO.getTotalAmount())
                .optionalServices(optionalServicesBuilder(loanOfferDTO))
                .creditStatus(CreditStatus.CALCULATED)
                .build();
        log.debug("RETURNING Credit, VALUE: {}", credit);

        return credit;
    }

    private OptionalServices optionalServicesBuilder(LoanOfferDTO loanOfferDTO) {

        log.debug("GETTING loanOfferDTO, INPUT VALUES: {}", loanOfferDTO);
        OptionalServices optionalServices = OptionalServices.builder()
                .isInsuranceEnabled(loanOfferDTO.getIsInsuranceEnabled())
                .isSalaryClient(loanOfferDTO.getIsSalaryClient())
                .build();
        log.debug("RETURNING OptionalServices, VALUE: {}", optionalServices);

        return optionalServices;
    }

    public void saveApplicationOffer(LoanOfferDTO loanOfferDTO) {

        log.debug("GETTING loanOfferDTO, INPUT VALUES: {}", loanOfferDTO);
        Application application = getApplication(loanOfferDTO.getApplicationId());
        application.setCredit(creditBuilder(loanOfferDTO));
        application.setSignDate(LocalDate.now());
        application.setAppliedOffer(loanOfferDTO.toString());
        application.setApplicationStatus(ApplicationStatus.CALCULATED);
        updateApplicationStatusHistory(application, ApplicationStatus.CALCULATED);
        log.debug("UPDATING and SAVING Application, VALUE: {}", application);

        applicationRepository.save(application);
    }

    public Application getApplication(Long applicationId) {

        log.debug("GETTING applicationID, VALUE: {}", applicationId);
        return applicationRepository.findById(applicationId).orElseThrow(() ->
                new NoSuchElementException("Cannot find an application with this ID"));
    }

    private Employment employmentBuilder(FinishRegistrationRequestDTO finishRegistrationRequestDTO) {

        log.debug("GETTING FinishRegistrationRequestDTO, VALUE: {}", finishRegistrationRequestDTO);

        Employment employment = Employment.builder()
                .employmentStatus(finishRegistrationRequestDTO.getEmployment().getEmploymentStatus())
                .employer(finishRegistrationRequestDTO.getEmployment().getEmployerINN())
                .salary(finishRegistrationRequestDTO.getEmployment().getSalary())
                .position(finishRegistrationRequestDTO.getEmployment().getPosition())
                .work_experience_total(finishRegistrationRequestDTO.getEmployment().getWorkExperienceTotal())
                .work_experience_current(finishRegistrationRequestDTO.getEmployment().getWorkExperienceCurrent())
                .build();

        log.debug("RETURNING Employment, VALUE: {}", employment);
        return employment;
    }

    public Application updateApplicationStatusHistory(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequestDTO) {

        log.debug("GETTING applicationId, VALUE: {}", applicationId);
        log.debug("GETTING finishRegistrationRequestDTO,  VALUE: {}", finishRegistrationRequestDTO);

        Application application = getApplication(applicationId);
        application.getClient().setGender(finishRegistrationRequestDTO.getGender());
        application.getClient().getPassport().setIssue_date(finishRegistrationRequestDTO.getPassportIssueDate());
        application.getClient().getPassport().setIssue_branch(finishRegistrationRequestDTO.getPassportIssueBranch());
        application.getClient().setMaritalStatus(finishRegistrationRequestDTO.getMaritalStatus());
        application.getClient().setDependentAmount(finishRegistrationRequestDTO.getDependentAmount());
        application.getClient().setEmployment(employmentBuilder(finishRegistrationRequestDTO));
        application.getClient().setAccount(finishRegistrationRequestDTO.getAccount());
        applicationRepository.save(application);

        log.debug("SAVING and RETURNING Application, VALUE: {}", application);
        return application;
    }

    public ScoringDataDTO scoringDataDTOBuilder(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequestDTO) {

        log.debug("GETTING applicationId, VALUE: {}", applicationId);
        log.debug("GETTING finishRegistrationRequestDTO,  VALUE: {}", finishRegistrationRequestDTO);

        Application application = updateApplicationStatusHistory(applicationId, finishRegistrationRequestDTO);
        log.debug("GETTING Application, UPDATING with updateApplicationStatusHistory, VALUE: {}", applicationId);

        ScoringDataDTO scoringDataDTO = ScoringDataDTO.builder()
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

        log.debug("RETURNING ScoringDataDto, VALUE: {}", scoringDataDTO);
        return scoringDataDTO;
    }

    public void finalUpdateApplication(Long applicationId, CreditDTO creditDTO, List<PaymentSchedule> paymentSchedules) {

        log.debug("GETTING applicationId, VALUE: {}", applicationId);
        log.debug("GETTING CreditDTO, VALUE: {}", creditDTO);
        log.debug("GETTING List<PaymentSchedule>, VALUE: {}", paymentSchedules);
        Application application = getApplication(applicationId);
        log.debug("GETTING application with getApplication(), VALUE: {}", applicationId);

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

        log.debug("UPDATING Application, VALUE: {}", application);
    }

    @Transactional
    public void updateApplicationStatusHistory(Application application, ApplicationStatus applicationStatus) {

        log.debug("GETTING Application, VALUE: {}", application);
        log.debug("GETTING ApplicationStatus, VALUE: {}", applicationStatus);

        List<ApplicationStatusHistory> list = new ArrayList<>();
        list.add(applicationStatusHistoryBuilder(applicationStatus));
        application.getStatusHistory().add(list.get(0));
        applicationRepository.save(application);

        log.debug("SAVING Application, VALUE: {}", application);
    }

    private ApplicationStatusHistory applicationStatusHistoryBuilder(ApplicationStatus applicationStatus) {

        log.debug("GETTING ApplicationStatus, VALUE: {}", applicationStatus);

        ApplicationStatusHistory applicationStatusHistory = applicationStatusHistoryRepository.save(ApplicationStatusHistory.builder()
                .status(applicationStatus)
                .time(LocalDateTime.now())
                .build());

        log.debug("ADDING new application status to ApplicationStatusHistory, VALUE: {}", applicationStatusHistory);
        return applicationStatusHistory;
    }

}
