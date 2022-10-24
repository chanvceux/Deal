package com.example.deal.facade;

import com.example.deal.dto.*;
import com.example.deal.service.ApplicationServiceImpl;
import com.example.deal.service.DealServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Facade {

    private final ApplicationServiceImpl applicationService;
    private final DealServiceImpl dealService;

    @Autowired
    public Facade(ApplicationServiceImpl applicationService, DealServiceImpl dealService) {
        this.applicationService = applicationService;
        this.dealService = dealService;
    }

    public void offersDealFacade(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.debug("GETTING LoanApplicationRequestDTO, VALUE: {}", loanApplicationRequestDTO);
        loanApplicationRequestDTO.setApplication_id(applicationService.addApplication(loanApplicationRequestDTO));
    }

    public CreditDTO calculateFacade(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId) {
        log.debug("GETTING FinishRegistrationRequestDTO, VALUE: {}", finishRegistrationRequestDTO);
        log.debug("GETTING applicationId, VALUE: {}", applicationId);
        ScoringDataDTO scoringDataDTO = applicationService.scoringDataDTOBuilder(applicationId, finishRegistrationRequestDTO);
        return dealService.calculation(scoringDataDTO);
    }



}
