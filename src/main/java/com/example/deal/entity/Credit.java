package com.example.deal.entity;

import com.example.deal.dto.PaymentScheduleElementDTO;
import com.example.deal.enumeration.CreditStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data

@Table(name = "credit")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal amount;

    @Column
    private Integer term;

    @Column
    private BigDecimal monthlyPayment;

    @Column
    private BigDecimal rate;

    @Column
    private BigDecimal psk;

    @Column
    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "optionalServices_id", unique = true, updatable = false)
    private OptionalServices optionalServices;


    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "paymentSchedule_id")
    private List<PaymentSchedule> paymentSchedule; // todo

    @OneToOne(cascade = {CascadeType.ALL}, optional = false, mappedBy = "credit")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    public Application application;

}
