package com.example.deal.service;
import com.example.deal.repository.PaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.deal.entity.PaymentSchedule;

@Service
@RequiredArgsConstructor
public class PaymentScheduleServiceImpl implements PaymentScheduleService{
    private final PaymentScheduleRepository paymentScheduleRepository;
    public PaymentSchedule addPaymentScheduleRepository(PaymentSchedule paymentSchedule) {
        return paymentScheduleRepository.save(paymentSchedule);
    }
}
