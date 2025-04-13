package com.travel.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travel.entities.Destinations;
import com.travel.entities.Trip;
import com.travel.repositories.DestinationsRepository;
import com.travel.repositories.TripRepository;
import com.travel.services.DestinationsService;

@Service
public class DestinationsServiceImpl implements DestinationsService {

    @Autowired
    private DestinationsRepository destRepo;
    
    @Autowired
    private TripRepository tripRepo;

    @Override
    public Destinations addDestination(Destinations dest, Long tripId) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        
        dest.setTrip(trip);
        return destRepo.save(dest);
    }
}