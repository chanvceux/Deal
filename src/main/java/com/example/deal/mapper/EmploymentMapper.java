package com.example.deal.mapper;

import com.example.deal.dto.FinishRegistrationRequestDTO;
import com.example.deal.entity.Employment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmploymentMapper {

    private EmploymentMapper() {
    }

    public static Employment employmentBuilder(FinishRegistrationRequestDTO finishRegistrationRequestDTO) {

        log.debug("GETTING FinishRegistrationRequestDTO, VALUE: {}", finishRegistrationRequestDTO);

        Employment employment = Employment.builder()
                .employmentStatus(finishRegistrationRequestDTO.getEmployment().getEmploymentStatus())
                .employer(finishRegistrationRequestDTO.getEmployment().getEmployer())
                .salary(finishRegistrationRequestDTO.getEmployment().getSalary())
                .position(finishRegistrationRequestDTO.getEmployment().getPosition())
                .workExperienceTotal(finishRegistrationRequestDTO.getEmployment().getWorkExperienceTotal())
                .workExperienceCurrent(finishRegistrationRequestDTO.getEmployment().getWorkExperienceCurrent())
                .build();

        log.debug("RETURNING Employment, VALUE: {}", employment);
        return employment;
    }
}
