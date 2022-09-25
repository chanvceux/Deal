package com.example.deal.feignclient;

import com.example.deal.dto.CreditDTO;
import com.example.deal.dto.LoanApplicationRequestDTO;
import com.example.deal.dto.LoanOfferDTO;
import com.example.deal.dto.ScoringDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient (name = "Conveyor", url = "${conveyor.url}")
public interface ConveyorMC {
    @PostMapping("/conveyor/offers")
    List<LoanOfferDTO> offers (@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO);

    @PostMapping("/conveyor/calculation")
    CreditDTO calculation (@RequestBody ScoringDataDTO scoringDataDTO);
}
