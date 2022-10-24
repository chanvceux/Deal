package com.example.deal.service;

import com.example.deal.dto.CreditDTO;
import com.example.deal.dto.LoanApplicationRequestDTO;
import com.example.deal.dto.LoanOfferDTO;
import com.example.deal.dto.ScoringDataDTO;
import com.example.deal.feign_client.ConveyorMC;
import com.example.deal.repository.ApplicationRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Slf4j
@Service
@Data
public class DealServiceImpl implements DealService {
    private final ConveyorMC conveyorMC;
    private final ApplicationRepository applicationRepository;
    @Autowired
    public DealServiceImpl(ConveyorMC conveyorMC, ApplicationRepository applicationRepository) {
        log.debug("GETTING conveyorMC, VALUE: {}", conveyorMC);
        log.debug("GETTING ApplicationRepository, VALUE: {}", applicationRepository);

        this.conveyorMC = conveyorMC;
        this.applicationRepository = applicationRepository;
    }

    public List<LoanOfferDTO>offers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        List<LoanOfferDTO> offers = conveyorMC.offers(loanApplicationRequestDTO);
        log.debug("RETURNING List<LoanOfferDTO>, VALUE: {}", offers);

        return offers;
    }

    public CreditDTO calculation(ScoringDataDTO scoringDataDTO) {
        CreditDTO creditDTO = conveyorMC.calculation(scoringDataDTO);
        log.debug("RETURNING CreditDTO, VALUE: {}", creditDTO);
        return creditDTO;
    }



}
