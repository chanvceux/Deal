package com.example.deal.service;
import com.example.deal.dto.*;
import com.example.deal.entity.*;
import com.example.deal.enumeration.ApplicationStatus;
import com.example.deal.mapper.*;
import com.example.deal.repository.ApplicationRepository;
import com.example.deal.repository.ApplicationStatusHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationStatusHistoryRepository applicationStatusHistoryRepository;
    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationStatusHistoryRepository applicationStatusHistoryRepository) {
        this.applicationRepository = applicationRepository;
        this.applicationStatusHistoryRepository = applicationStatusHistoryRepository;
    }

    public Long addApplication(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        log.debug("GETTING LoanApplicationRequestDTO, INPUT VALUES: {}", loanApplicationRequestDTO);
        Application application = ApplicationMapper.applicationBuilder(loanApplicationRequestDTO, ApplicationStatus.PREAPPROVAL);

        log.debug("BUILDING application, VALUE: {}", application);
        applicationRepository.save(application);

        log.debug("RETURNING application id, VALUE: {}", application.getId());
        return application.getId();
    }

    public void saveApplicationOffer(LoanOfferDTO loanOfferDTO) {

        log.debug("GETTING loanOfferDTO, INPUT VALUES: {}", loanOfferDTO);
        Application application = getApplication(loanOfferDTO.getApplicationId());
        application.setCredit(CreditMapper.creditBuilder(loanOfferDTO));
        application.setSignDate(LocalDate.now());
        application.setSesCode(ApplicationMapper.createSesCode().toString());
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

    public ApplicationDTO getApplicationDTO(Long applicationID) {
        return ApplicationMapper.applicationToApplicationDTO(getApplication(applicationID));
    }
    public List<ApplicationDTO> getALlApplications() {
        List<ApplicationDTO> applicationDTOS = new ArrayList<>();
        List<Application> application = applicationRepository.findAll();
        for (Application element: application) {
            applicationDTOS.add(ApplicationMapper.applicationToApplicationDTO(element));
        }
        return applicationDTOS;
    }
    public Application updateApplicationStatusHistory(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequestDTO) {

        log.debug("GETTING applicationId, VALUE: {}", applicationId);
        log.debug("GETTING finishRegistrationRequestDTO,  VALUE: {}", finishRegistrationRequestDTO);

        Application application = getApplication(applicationId);
        application.getClient().setGender(finishRegistrationRequestDTO.getGender());
        application.getClient().getPassport().setIssueDate(finishRegistrationRequestDTO.getPassportIssueDate());
        application.getClient().getPassport().setIssueBranch(finishRegistrationRequestDTO.getPassportIssueBranch());
        application.getClient().setMaritalStatus(finishRegistrationRequestDTO.getMaritalStatus());
        application.getClient().setDependentAmount(finishRegistrationRequestDTO.getDependentAmount());
        application.getClient().setEmployment(EmploymentMapper.employmentBuilder(finishRegistrationRequestDTO));
        application.getClient().setAccount(finishRegistrationRequestDTO.getAccount());
        applicationRepository.save(application);

        log.debug("SAVING and RETURNING Application, VALUE: {}", application);
        return application;
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
        application.getCredit().getOptionalServices().setIsSalaryClient(creditDTO.getIsInsuranceEnabled());
        application.getCredit().setPaymentSchedule(paymentSchedules);
        updateApplicationStatusHistory(application, ApplicationStatus.APPROVED);

        log.debug("UPDATING Application, VALUE: {}", application);
    }

    public void updateApplicationStatusHistory(Application application, ApplicationStatus applicationStatus) {

        log.debug("GETTING Application, VALUE: {}", application);
        log.debug("GETTING ApplicationStatus, VALUE: {}", applicationStatus);

        List<ApplicationStatusHistory> list = new ArrayList<>();
        list.add(applicationStatusHistorySave(applicationStatus));
        application.getStatusHistory().add(list.get(0));
        applicationRepository.save(application);

        log.debug("SAVING Application, VALUE: {}", application);
    }

    private ApplicationStatusHistory applicationStatusHistorySave(ApplicationStatus applicationStatus) {

        log.debug("GETTING ApplicationStatus, VALUE: {}", applicationStatus);
        ApplicationStatusHistory applicationStatusHistory = applicationStatusHistoryRepository.save(ApplicationStatusHistoryMapper.applicationStatusHistoryBuilder(applicationStatus));

        log.debug("ADDING new application status to ApplicationStatusHistory, VALUE: {}", applicationStatusHistory);
        return applicationStatusHistory;
    }

}
