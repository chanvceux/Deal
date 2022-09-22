package com.example.deal.service;


import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;


public interface DealService {
    static Integer calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }


}
