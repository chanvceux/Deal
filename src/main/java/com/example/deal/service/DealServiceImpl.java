package com.example.deal.service;

import com.example.deal.dto.CreditDTO;
import com.example.deal.dto.LoanApplicationRequestDTO;
import com.example.deal.dto.LoanOfferDTO;
import com.example.deal.dto.ScoringDataDTO;
import com.example.deal.feignclient.ConveyorMC;
import com.example.deal.repository.ApplicationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@Data
public class DealServiceImpl implements DealService {
    private final ConveyorMC conveyorMC;
    private final ApplicationRepository applicationRepository;
    @Autowired
    public DealServiceImpl(ConveyorMC conveyorMC, ApplicationRepository applicationRepository) {
        this.conveyorMC = conveyorMC;
        this.applicationRepository = applicationRepository;
    }

    public List<LoanOfferDTO>offers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        return conveyorMC.offers(loanApplicationRequestDTO);
    }

    public CreditDTO calculation (ScoringDataDTO scoringDataDTO) {
        return conveyorMC.calculation(scoringDataDTO);
    }



}
