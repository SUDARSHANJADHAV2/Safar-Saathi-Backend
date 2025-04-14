package com.travel.services;
import com.travel.entities.Destinations;

public interface DestinationsService {
    Destinations addDestination(Destinations dest, Long tripId);
}