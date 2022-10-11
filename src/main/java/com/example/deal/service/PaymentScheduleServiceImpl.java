package com.example.deal.service;
import com.example.deal.repository.PaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.deal.entity.PaymentSchedule;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentScheduleServiceImpl implements PaymentScheduleService{
    private final PaymentScheduleRepository paymentScheduleRepository;
    public PaymentSchedule addPaymentScheduleRepository(PaymentSchedule paymentSchedule) {
        log.debug("SAVING PaymentSchedule, VALUE: {}", paymentSchedule);
        return paymentScheduleRepository.save(paymentSchedule);
    }
}
