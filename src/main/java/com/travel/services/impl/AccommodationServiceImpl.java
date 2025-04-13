package com.travel.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.entities.Accommodation;
import com.travel.entities.Trip;
import com.travel.repositories.AccommodationRepository;
import com.travel.repositories.TripRepository;
import com.travel.services.AccommodationService;

@Service
public class AccommodationServiceImpl implements AccommodationService {

    @Autowired
    private AccommodationRepository accommodationRepo;

    @Autowired
    private TripRepository tripRepo;

    @Override
    public Accommodation addAccommodation(Accommodation acc, Long tripId) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        acc.setTrip(trip);

        return accommodationRepo.save(acc);
    }
}