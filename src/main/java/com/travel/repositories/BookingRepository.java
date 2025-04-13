package com.travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.entities.Bookings;

public interface BookingRepository extends JpaRepository<Bookings, Long> {
}