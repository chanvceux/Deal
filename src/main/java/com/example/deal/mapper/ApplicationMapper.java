package com.example.deal.mapper;

import com.example.deal.dto.*;
import com.example.deal.entity.Application;
import com.example.deal.enumeration.ApplicationStatus;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApplicationMapper {

    static ModelMapper modelMapper = new ModelMapper();
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

    public static ApplicationDTO applicationToApplicationDTO(Application application) {
        List<ApplicationStatusHistoryDTO> applicationStatusHistoryDTOS = new ArrayList<>();

        for (int i = 0; i < application.getStatusHistory().size(); i++) {
            applicationStatusHistoryDTOS.add(modelMapper.map(application.getStatusHistory().get(i),
                    ApplicationStatusHistoryDTO.class));
        }

        return ApplicationDTO.builder()
                .id(application.getId())
                .client(modelMapper.map(application.getClient(), ClientDTO.class))
                .credit(CreditMapper.creditToCreditDTO(application.getCredit()))
                .applicationStatus(application.getApplicationStatus())
                .creationDate(application.getCreationDate())
                .appliedOffer(application.getAppliedOffer())
                .signDate(application.getSignDate())
                .sesCode(application.getSesCode())
                .statusHistory(applicationStatusHistoryDTOS)
                .build();
    }

    public static Integer createSesCode() {
        int min = 1000;
        int max = 9999;
        return (int) (Math.random()*++max + min);
    }
}
