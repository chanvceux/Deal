package com.example.deal.constraint;

import com.example.deal.service.DealService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class PersonAgeConstraintValidator implements ConstraintValidator <PersonAgeConstraint, LocalDate> {

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        return DealService.calculateAge(birthDate) >= 18;
    }
}
