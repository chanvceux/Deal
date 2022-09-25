package com.example.deal.controller;

import com.example.deal.dto.*;
import com.example.deal.service.ApplicationServiceImpl;
import com.example.deal.service.CreditServiceImpl;
import com.example.deal.service.DealServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "DealController", description = "Interaction with MVP1, " +
        "database operations and data transfer to describe the logic")
@Slf4j
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
    @Operation(description = "Creating offers, interaction with method \"offers\" from Conveyor")
    public List<LoanOfferDTO> offersDeal(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.debug("GETTING LoanApplicationRequestDTO, VALUE: {}", loanApplicationRequestDTO);
        loanApplicationRequestDTO.setApplication_id(applicationService.addApplication(loanApplicationRequestDTO));
        return dealService.offers(loanApplicationRequestDTO);
    }

    @PutMapping("/offer")
    @Operation(description = "Saving offers to PostgreSQL database")
    public void addOffer(@RequestBody LoanOfferDTO loanOfferDTO) {
        log.debug("GETTING LoanOfferDTO, VALUE: {}", loanOfferDTO);
        applicationService.saveApplicationOffer(loanOfferDTO);
    }

    @PutMapping("/calculate/{applicationId}")
    @Operation(description = "Adding new information to the existed offer, interaction with " +
            "method \"calculation\" from Conveyor")
    public void calculate(@RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO, @PathVariable Long applicationId) {
        log.debug("GETTING FinishRegistrationRequestDTO, VALUE: {}", finishRegistrationRequestDTO);
        log.debug("GETTING applicationId, VALUE: {}", applicationId);
        ScoringDataDTO scoringDataDTO = applicationService.scoringDataDTOBuilder(applicationId, finishRegistrationRequestDTO);
        CreditDTO creditDTO = dealService.calculation(scoringDataDTO);
        creditService.updateCredit(creditDTO, applicationId);
    }


}
