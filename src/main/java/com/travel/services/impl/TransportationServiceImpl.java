package com.travel.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travel.entities.Transportation;
import com.travel.entities.Trip;
import com.travel.repositories.TransportationRepository;
import com.travel.repositories.TripRepository;
import com.travel.services.TransportationService;
import java.util.List;

@Service
public class TransportationServiceImpl implements TransportationService {

    @Autowired
    private TransportationRepository transportRepo;

    @Autowired
    private TripRepository tripRepo;

    @Override
    public Transportation addTransport(Transportation transport, Long tripId) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with ID: " + tripId));

        transport.setTrip(trip);

        return transportRepo.save(transport);
    }

    @Override
    public List<Transportation> getTransportByTrip(Long tripId) {
        return null;
    }
}