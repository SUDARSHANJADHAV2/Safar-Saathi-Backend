package com.travel.services;

import com.travel.entities.Transportation;
import java.util.List;

public interface TransportationService {
    Transportation addTransport(Transportation transport, Long tripId);
    List<Transportation> getTransportByTrip(Long tripId);
}