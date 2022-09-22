package com.example.deal.entity;

import com.example.deal.enumeration.ApplicationStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id")
    Client client;
    Credit credit;
    ApplicationStatus applicationStatus;
    LocalDate creation_date;
    //appliedOffer;
    LocalDate sign_date;
    String ses_code;
    ApplicationStatusHistory statusHistory;

}
