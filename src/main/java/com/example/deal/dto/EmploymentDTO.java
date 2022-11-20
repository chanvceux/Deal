package com.example.deal.dto;

import com.example.deal.enumeration.EmploymentStatus;
import com.example.deal.enumeration.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmploymentDTO {
    private EmploymentStatus employmentStatus;
    private String employer;
    private BigDecimal salary;
    private Position position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;

}
