package com.example.deal.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "optionalServices")
public class OptionalServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean isInsuranceEnabled;

    @Column
    private Boolean isSalaryClient;

    @OneToOne(cascade = {CascadeType.ALL}, optional = false, mappedBy = "optionalServices")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    public Credit credit;

}
