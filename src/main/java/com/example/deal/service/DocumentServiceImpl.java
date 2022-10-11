package com.example.deal.service;

import com.example.deal.dto.DocumentCreatingDTO;
import com.example.deal.dto.PaymentScheduleElementDTO;
import com.example.deal.entity.Application;
import com.example.deal.mapper.DocumentMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {
    private final ApplicationServiceImpl applicationService;
    private final ModelMapper modelMapper = new ModelMapper();

    public DocumentServiceImpl(ApplicationServiceImpl applicationService) {
        this.applicationService = applicationService;
    }

    public DocumentCreatingDTO getInfoForDocument(Long applicationId) {

        Application application = applicationService.getApplication(applicationId);
        log.trace("GETTING applicationId, VALUE: {}; \n CREATING application, VALUE: {}", applicationId, application);
        List<PaymentScheduleElementDTO> paymentScheduleElementList = new ArrayList<>();

        for (int i = 0; i < application.getCredit().getPaymentSchedule().size(); i++) {
            paymentScheduleElementList.add(modelMapper.map(application.getCredit().getPaymentSchedule().get(i), PaymentScheduleElementDTO.class));
        }

        DocumentCreatingDTO documentCreatingDTO = DocumentMapper.documentBuilder(application, modelMapper, paymentScheduleElementList);
        log.trace("CREATING document, VALUE: {}", documentCreatingDTO);

        return documentCreatingDTO;
    }

}
