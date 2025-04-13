package com.travel.services;

import com.travel.entities.Accommodation;

public interface AccommodationService {
    Accommodation addAccommodation(Accommodation accommodation, Long tripId);
}