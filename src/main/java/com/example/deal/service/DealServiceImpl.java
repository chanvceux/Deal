package com.example.deal.service;


import com.example.deal.dto.LoanApplicationRequestDTO;
import com.example.deal.entity.*;
import com.example.deal.enumeration.ApplicationStatus;
import com.example.deal.repository.ApplicationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@AllArgsConstructor
@Data
public class DealServiceImpl implements DealService {


    private final ApplicationRepository applicationRepository;

    private Passport passportBuilder(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        Passport.builder()
                .number(loanApplicationRequestDTO.getPassportNumber())
                .series(loanApplicationRequestDTO.getPassportSeries())
//                .issue_date() //todo
//                .issue_branch() //todo
                .build();

        return null;
    }

    private Employment employmentBuilder(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        return null;
//        Employment.builder()
//                .employmentStatus()
//                .employer()
//                .salary()
//                .position()
//                .work_experience_total()
//                .work_experience_current()
//                .build();
    }
    private Client clientBuilder(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        return Client.builder()
                .first_name(loanApplicationRequestDTO.getFirstName())
                .middle_name(loanApplicationRequestDTO.getMiddleName())
                .last_name(loanApplicationRequestDTO.getLastName())
                .email(loanApplicationRequestDTO.getEmail())
                .passport(passportBuilder(loanApplicationRequestDTO))
                .employment(employmentBuilder(loanApplicationRequestDTO))
//                .maritalStatus() //todo
//                .dependentAmount() //todo
//                .account() //todo
                .build();
    }

    private ApplicationStatusHistory applicationStatusHistoryBuilder() {
        return ApplicationStatusHistory.builder()
                .status(ApplicationStatus.PREAPPROVAL)
                .time(LocalDateTime.now())
                .build();
    }
    public Long applicationBuilder(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        Application application = Application.builder()
                .client(clientBuilder(loanApplicationRequestDTO))
                .applicationStatus(ApplicationStatus.PREAPPROVAL)
                .creation_date(LocalDate.now())
                .statusHistory(Collections.singletonList(applicationStatusHistoryBuilder()))
                .build();

        applicationRepository.save(application);
        return application.getId();
    }



}
