package com.example.deal.entity;

import com.example.deal.enumeration.ApplicationStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    Client client;
    Credit credit;
    ApplicationStatus applicationStatus;
    LocalDate creation_date;
    //appliedOffer;
    LocalDate sign_date;
    String ses_code;
    ApplicationStatusHistory statusHistory;

}
