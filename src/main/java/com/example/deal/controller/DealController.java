package com.example.deal.controller;

import com.example.deal.dto.*;
import com.example.deal.service.ApplicationServiceImpl;
import com.example.deal.service.CreditServiceImpl;
import com.example.deal.service.DealServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/deal")
public class DealController {
    @Autowired
    DealServiceImpl dealService;
    @Autowired
    CreditServiceImpl creditService;
    @Autowired
    ApplicationServiceImpl applicationService;

    @PostMapping("/application")
    public List<LoanOfferDTO> offersDeal(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        loanApplicationRequestDTO.setApplication_id(applicationService.addApplication(loanApplicationRequestDTO));
        return dealService.offers(loanApplicationRequestDTO);
    }

    @PutMapping("/offer")
    public void addOffer(@RequestBody LoanOfferDTO loanOfferDTO) {
        applicationService.saveApplicationOffer(loanOfferDTO);
    }

    @PutMapping("/calculate/{applicationId}")
    public void calculate(@RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO, @PathVariable Long applicationId) {
        ScoringDataDTO scoringDataDTO = applicationService.scoringDataDTOBuilder(applicationId, finishRegistrationRequestDTO);
        CreditDTO creditDTO = dealService.calculation(scoringDataDTO);
        creditService.updateCredit(creditDTO, scoringDataDTO, applicationId);
    }


}
