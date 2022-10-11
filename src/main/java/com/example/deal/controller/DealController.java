package com.example.deal.controller;

import com.example.deal.dto.*;
import com.example.deal.enumeration.Theme;
import com.example.deal.facade.Facade;
import com.example.deal.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final KafkaProducerServiceImpl kafkaProducerService;
    private final DocumentServiceImpl documentService;

    @Autowired
    public DealController(DealServiceImpl dealService, CreditServiceImpl creditService, ApplicationServiceImpl applicationService, Facade facade, KafkaProducerServiceImpl kafkaProducerService, DocumentServiceImpl documentService) {
        this.dealService = dealService;
        this.creditService = creditService;
        this.applicationService = applicationService;
        this.facade = facade;
        this.kafkaProducerService = kafkaProducerService;
        this.documentService = documentService;
    }

    @PostMapping("/application")
    @Operation(description = "Creating offers, interaction with method \"offers\" from Conveyor")
    public List<LoanOfferDTO> offersDeal(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        facade.offersDealFacade(loanApplicationRequestDTO);
        return dealService.offers(loanApplicationRequestDTO);
    }

    @PutMapping("/offer")
    @Operation(description = "Saving offers to PostgreSQL database")
    public void addOffer(@RequestBody LoanOfferDTO loanOfferDTO) throws JsonProcessingException {
        log.debug("GETTING LoanOfferDTO, VALUE: {}", loanOfferDTO);
        applicationService.saveApplicationOffer(loanOfferDTO);
        kafkaProducerService.send("finish-registration", Theme.FINISH_REGISTRATION, loanOfferDTO.getApplicationId());
    }

    @PutMapping("/calculate/{applicationId}")
    @Operation(description = "Adding new information to the existed offer, interaction with " +
            "method \"calculation\" from Conveyor")
    public void calculate(@RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO,
                          @PathVariable Long applicationId) throws JsonProcessingException {
        creditService.updateCredit(facade.calculateFacade(finishRegistrationRequestDTO, applicationId), applicationId);
        kafkaProducerService.send("create-documents", Theme.CREATE_DOCUMENTS, applicationId);
    }


    @PostMapping("/deal/document/{applicationId}/send")
    @Operation(description = "Creating request to send documents")
    public void documentSend(@PathVariable Long applicationId) throws JsonProcessingException {
        kafkaProducerService.send("send-documents", Theme.SEND_DOCUMENTS, applicationId);
    }

    @PostMapping("/deal/document/{applicationId}/sign")
    @Operation(description = "Creating request to send ses-code")
    public void documentSign(@PathVariable Long applicationId) throws JsonProcessingException {
        kafkaProducerService.send("send-ses", Theme.SEND_SES, applicationId);
    }

    @PostMapping("/deal/document/{applicationId}/code")
    @Operation(description = "Creating request to verify ses-code")

    public void documentCode(@PathVariable Long applicationId, @RequestBody Integer sesCode) throws JsonProcessingException {
        Integer generatedSesCode = Integer.valueOf(applicationService.getApplication(applicationId).getSesCode());
        log.trace("GENERATED ses-code, VALUE: {}", generatedSesCode);
        facade.documentCodeFacade(kafkaProducerService, applicationId, sesCode, generatedSesCode);
    }
    @PostMapping("/application/{applicationId}")
    @Operation(description = "DossierMC")
    public ResponseEntity<DocumentCreatingDTO> getApplication(@PathVariable Long applicationId) {
        DocumentCreatingDTO documentCreatingDTO = documentService.getInfoForDocument(applicationId);
        return new ResponseEntity<>(documentCreatingDTO, HttpStatus.OK);
    }

}
