package com.example.deal.mapper;

import com.example.deal.dto.LoanApplicationRequestDTO;
import com.example.deal.entity.Application;
import com.example.deal.enumeration.ApplicationStatus;

import java.time.LocalDate;
import java.util.List;

public class ApplicationMapper {

    private ApplicationMapper() {
    }

    public static Application applicationBuilder(LoanApplicationRequestDTO loanApplicationRequestDTO, ApplicationStatus applicationStatus) {
    return Application.builder()
            .client(ClientMapper.clientBuilder(loanApplicationRequestDTO))
            .applicationStatus(applicationStatus)
            .creationDate(LocalDate.now())
            .statusHistory(List.of(ApplicationStatusHistoryMapper.applicationStatusHistoryBuilder(ApplicationStatus.PREAPPROVAL)))
            .build();
    }

    public static Integer createSesCode() {
        int min = 1000;
        int max = 9999;
        return (int) (Math.random()*++max + min);
    }
}
