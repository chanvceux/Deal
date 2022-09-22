package com.example.deal.entity;

import com.example.deal.enumeration.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data

@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL}, optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @Column
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    @Column
    private LocalDate creation_date;

    @Column
    private String appliedOffer;

    @Column
    private LocalDate sign_date;

    @Column
    private String ses_code;

    @OneToMany (cascade = {CascadeType.ALL})
    @JoinColumn(name = "statusHistory_id")
    private List<ApplicationStatusHistory> statusHistory;

}
