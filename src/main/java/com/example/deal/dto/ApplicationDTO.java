package com.example.deal.dto;

import com.example.deal.enumeration.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {

    private Long id;
    private ClientDTO client;
    private CreditDTO credit;
    private ApplicationStatus applicationStatus;
    private LocalDate creationDate;
    private String appliedOffer;
    private LocalDate signDate;
    private String sesCode;
    private List<ApplicationStatusHistoryDTO> statusHistory;
}
