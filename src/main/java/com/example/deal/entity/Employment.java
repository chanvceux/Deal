package com.example.deal.entity;

import com.example.deal.enumeration.EmploymentStatus;
import com.example.deal.enumeration.Position;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    EmploymentStatus employmentStatus;
    String employer;
    BigDecimal salary;
    Position position;
    Integer work_experience_total;
    Integer Work_experience_current;
}
