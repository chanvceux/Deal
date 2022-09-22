package com.example.deal.repository;

import com.example.deal.entity.OptionalServices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionalServiceRepository extends JpaRepository<OptionalServices, Long> {
}
