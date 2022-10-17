package com.example.deal.mapper;

import com.example.deal.dto.FinishRegistrationRequestDTO;
import com.example.deal.dto.ScoringDataDTO;
import com.example.deal.entity.Application;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class ScoringDataMapper {

    private ScoringDataMapper() {
    }
    public static ScoringDataDTO scoringDataBuilder(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequestDTO, Application application) {

        log.debug("GETTING applicationId, VALUE: {}", applicationId);
        log.debug("GETTING finishRegistrationRequestDTO,  VALUE: {}", finishRegistrationRequestDTO);


        ScoringDataDTO scoringDataDTO = ScoringDataDTO.builder()
                .amount(application.getCredit().getAmount())
                .term(application.getCredit().getTerm())
                .firstName(application.getClient().getFirstName())
                .middleName(application.getClient().getMiddleName())
                .lastName(application.getClient().getLastName())
                .gender(application.getClient().getGender())
                .birthdate(application.getClient().getBirthdate())
                .passportSeries(application.getClient().getPassport().getSeries())
                .passportNumber(application.getClient().getPassport().getNumber())
                .passportIssueBranch(application.getClient().getPassport().getIssueBranch())
                .passportIssueDate(application.getClient().getPassport().getIssueDate())
                .maritalStatus(application.getClient().getMaritalStatus())
                .dependentAmount(application.getClient().getDependentAmount())
                .employment(finishRegistrationRequestDTO.getEmployment())
                .account(application.getClient().getAccount())
                .isInsuranceEnabled(application.getCredit().getOptionalServices().getIsInsuranceEnabled())
                .isSalaryClient(application.getCredit().getOptionalServices().getIsSalaryClient())
                .build();

        log.debug("RETURNING ScoringDataDto, VALUE: {}", scoringDataDTO);
        return scoringDataDTO;
    }
}
