package com.example.deal.entity;
import com.example.deal.enumeration.ApplicationStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL}, optional = false)
    @JoinColumn(name = "client_id", unique = true)
    private Client client;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id", unique = true)
    private Credit credit;

    @Column
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    @Column
    private LocalDate creation_date;

    @Column
    private String appliedOffer;

    @Column
    private LocalDate signDate;

    @Column
    private String sesCode;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "application_id")
    private List<ApplicationStatusHistory> statusHistory;

}
