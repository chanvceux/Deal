package com.example.deal.controller;

import com.example.deal.dto.*;
import com.example.deal.facade.Facade;
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
    private final DealServiceImpl dealService;
    private final CreditServiceImpl creditService;
    private final ApplicationServiceImpl applicationService;
    private final Facade facade;

    @Autowired
    public DealController(DealServiceImpl dealService, CreditServiceImpl creditService, ApplicationServiceImpl applicationService, Facade facade) {
        this.dealService = dealService;
        this.creditService = creditService;
        this.applicationService = applicationService;
        this.facade = facade;
    }

    @PostMapping("/application")
    @Operation(description = "Creating offers, interaction with method \"offers\" from Conveyor")
    public List<LoanOfferDTO> offersDeal(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        facade.offersDealFacade(loanApplicationRequestDTO);
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
        creditService.updateCredit(facade.calculateFacade(finishRegistrationRequestDTO, applicationId), applicationId);
    }


}
