package com.travel.services;

import com.travel.dtos.DestinationDto;
import com.travel.dtos.TripDto;
import com.travel.entities.Trip;
import java.util.List;

public interface TripService {
   TripDto createTrip(Trip trip, Long customerId, Long packageId);

   List<TripDto> getTripsByCustomer(Long customerId);

   TripDto getTripById(Long tripId);

   void addDestination(Long tripId, DestinationDto destDto);

   TripDto updateTrip(Long tripId, TripDto tripDto);

   void deleteTrip(Long tripId);
}