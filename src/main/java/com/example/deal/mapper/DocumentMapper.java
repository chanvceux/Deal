package com.example.deal.mapper;

import com.example.deal.dto.DocumentCreatingDTO;
import com.example.deal.dto.EmploymentDTO;
import com.example.deal.dto.PaymentScheduleElementDTO;
import com.example.deal.entity.Application;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.util.List;

@Slf4j
public class DocumentMapper {

    private DocumentMapper() {
    }

    public static DocumentCreatingDTO documentBuilder(Application application,
                                                      ModelMapper mapper,
                                                      List<PaymentScheduleElementDTO> paymentScheduleElementList) {

        DocumentCreatingDTO documentCreatingDTO = DocumentCreatingDTO.builder()
                .fullName(application.getClient().getLastName() + " " + application.getClient().getFirstName() + " " + application.getClient().getMiddleName())
                .birthdate(application.getClient().getBirthdate())
                .gender(application.getClient().getGender().toString())
                .fullPassportData(application.getClient().getPassport().getNumber() + " " + application.getClient().getPassport().getSeries()
                        + " выдан " + application.getClient().getPassport().getIssueBranch() + " " + application.getClient().getPassport().getIssueDate())
                .email(application.getClient().getEmail())
                .martialStatus(application.getClient().getMaritalStatus().toString())
                .dependentAmount(application.getClient().getDependentAmount())
                .employment(mapper.map(application.getClient().getEmployment(), EmploymentDTO.class))
                .amount(application.getCredit().getAmount())
                .term(application.getCredit().getTerm())
                .monthlyPayment(application.getCredit().getMonthlyPayment())
                .rate(application.getCredit().getRate())
                .psk(application.getCredit().getPsk())
                .isInsuranceEnabled(application.getCredit().getOptionalServices().getIsInsuranceEnabled())
                .isSalaryClient(application.getCredit().getOptionalServices().getIsSalaryClient())
                .paymentScheduleElementList(paymentScheduleElementList)
                .sesCode(Integer.valueOf(application.getSesCode()))
                .build();

        log.trace("CREATED document for sending, VALUE: {}", documentCreatingDTO);
        return documentCreatingDTO;
    }
}
