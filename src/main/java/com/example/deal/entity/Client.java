package com.example.deal.entity;

import com.example.deal.enumeration.Gender;
import com.example.deal.enumeration.MaritalStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String last_name;

    @Column
    private String first_name;

    @Column
    private String middle_name;

    @Column
    private LocalDate birthdate;

    @Column
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column
    private Integer dependentAmount;

    @OneToOne(cascade = {CascadeType.ALL}, optional = false)
    @JoinColumn(name = "passport_id", unique = true, nullable = false)
    private Passport passport;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_id", unique = true)
    private Employment employment;

    @Column
    private String account;

    @OneToOne(cascade = {CascadeType.ALL}, optional = false, mappedBy = "client")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    public Application application;
}