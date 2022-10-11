package com.example.deal.mapper;

import com.example.deal.entity.ApplicationStatusHistory;
import com.example.deal.enumeration.ApplicationStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j

public class ApplicationStatusHistoryMapper {

    private ApplicationStatusHistoryMapper() {
    }

    public static ApplicationStatusHistory applicationStatusHistoryBuilder(ApplicationStatus applicationStatus) {

        log.debug("GETTING ApplicationStatus, VALUE: {}", applicationStatus);

        ApplicationStatusHistory applicationStatusHistory = ApplicationStatusHistory.builder()
                .status(applicationStatus)
                .time(LocalDateTime.now())
                .build();

        log.debug("ADDING new application status to ApplicationStatusHistory, VALUE: {}", applicationStatusHistory);
        return applicationStatusHistory;
    }
}
