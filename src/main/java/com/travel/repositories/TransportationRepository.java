package com.travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.entities.Transportation;

public interface TransportationRepository extends JpaRepository<Transportation, Long> {
}