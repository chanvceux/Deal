package com.example.deal.controller;

import com.example.deal.dto.FinishRegistrationRequestDTO;
import com.example.deal.dto.LoanApplicationRequestDTO;
import com.example.deal.dto.LoanOfferDTO;
import com.example.deal.service.DealServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DealController {

    @Autowired
    DealServiceImpl dealService;


    @PostMapping("/deal/application")
    public List<LoanOfferDTO> calculationDeal(@RequestParam LoanApplicationRequestDTO loanApplicationRequestDTO) {
        return null;
    }

    @PutMapping("/deal/offer")
    public void someName(@RequestParam LoanOfferDTO loanOfferDTO) {
    }

    @PutMapping("/deal/calculate/{applicationId}")
    public void someSecondName(@RequestParam FinishRegistrationRequestDTO finishRegistrationRequestDTO, @PathVariable Long applicationId) {
    }


}
