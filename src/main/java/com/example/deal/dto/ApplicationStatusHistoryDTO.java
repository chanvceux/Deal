package com.example.deal.dto;

import com.example.deal.enumeration.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationStatusHistoryDTO {
    private Long id;
    private ApplicationStatus status;
    private LocalDateTime time;
}
