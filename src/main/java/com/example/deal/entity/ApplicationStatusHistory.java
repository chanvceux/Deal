package com.example.deal.entity;

import com.example.deal.enumeration.ApplicationStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "applicationStatusHistory")
public class ApplicationStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column
    private LocalDateTime time;

}
