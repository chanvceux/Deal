package com.example.deal.entity;

import com.example.deal.enumeration.Gender;
import com.example.deal.enumeration.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String last_name;
    String first_name;
    LocalDate birth_date;
    String email;
    Gender gender;
    MaritalStatus maritalStatus;
    Integer dependentAmount;
    //Passport passport; // todo
    //Employment employment; // todo
    String account;

}
