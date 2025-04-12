package com.travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.entities.Accommodation;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}