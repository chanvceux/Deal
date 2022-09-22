package com.example.deal.entity;

import com.example.deal.enumeration.Gender;
import com.example.deal.enumeration.MaritalStatus;
import lombok.*;

import javax.persistence.*;
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

    @Column
    String last_name;

    @Column
    String first_name;

    @Column
    LocalDate birth_date;

    @Column
    String email;

    @Column
    Gender gender;

    @Column
    MaritalStatus maritalStatus;

    @Column
    Integer dependentAmount;

    @OneToOne(cascade = {CascadeType.ALL}, optional = false)
    @JoinColumn(name = "passport_id")
    Passport passport;


    @OneToOne(cascade = {CascadeType.ALL}, optional = false)
    @JoinColumn(name = "employment_id")
    Employment employment; // todo

    String account;


    @OneToOne(cascade = {CascadeType.ALL}, optional = false, mappedBy = "client")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    public Application application;

}
