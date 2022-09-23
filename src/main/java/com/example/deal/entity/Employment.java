package com.example.deal.entity;

import com.example.deal.enumeration.EmploymentStatus;
import com.example.deal.enumeration.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer work_experience_total;

    @Column
    private Integer work_experience_current;
}
