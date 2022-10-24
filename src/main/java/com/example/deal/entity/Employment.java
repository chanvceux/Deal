package com.example.deal.entity;

import com.example.deal.enumeration.EmploymentStatus;
import com.example.deal.enumeration.Position;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "employment")
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;

    @Column
    private String employer;

    @Column
    private BigDecimal salary;

    @Column
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column
    private Integer workExperienceTotal;

    @Column
    private Integer workExperienceCurrent;

    @OneToOne(cascade = {CascadeType.ALL}, optional = false, mappedBy = "employment")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    public Client client;
}
