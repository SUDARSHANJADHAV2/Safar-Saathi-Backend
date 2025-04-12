package com.travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.entities.Destinations;

public interface DestinationsRepository extends JpaRepository<Destinations, Long> {
}