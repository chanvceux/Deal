package com.example.deal.entity;

import com.example.deal.enumeration.Gender;
import com.example.deal.enumeration.MaritalStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    private String last_name;

    @Column
    private String first_name;

    @Column
    private LocalDate birth_date;

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
    @JoinColumn(name = "passport_id")
    private Passport passport;

    @OneToOne(cascade = {CascadeType.ALL}, optional = false)
    @JoinColumn(name = "employment_id")
    private Employment employment; // todo

    @Column
    private String account;

    @OneToOne(cascade = {CascadeType.ALL}, optional = false, mappedBy = "client")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Application application;

}
