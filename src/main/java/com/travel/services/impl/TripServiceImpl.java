package com.travel.services.impl;

import com.travel.dtos.DestinationDto;
import com.travel.dtos.TripDto;
import com.travel.entities.Trip;
import com.travel.entities.User;
import com.travel.entities.Packages;
import com.travel.entities.BookingStatus;
import com.travel.entities.TripStatus;
import com.travel.repositories.TripRepository;
import com.travel.repositories.UserRepository;
import com.travel.repositories.PackagesRepository;
import com.travel.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PackagesRepository packagesRepository;

    @Override
    public TripDto createTrip(Trip trip, Long customerId, Long packageId) {
        User user = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
        trip.setCustomer(user);

        if (packageId != null) {
            Packages pkg = packagesRepository.findById(packageId)
                    .orElseThrow(() -> new RuntimeException("Package not found with ID: " + packageId));
            trip.setSelectedPackage(pkg);

            if (trip.getTripName() == null)
                trip.setTripName(pkg.getPackageName());
            if (trip.getBudget() == null)
                trip.setBudget(pkg.getPrice());
        }

        if (trip.getTripStatus() == null) {
            trip.setTripStatus(TripStatus.SCHEDULED);
        }

        if (trip.getStartDate() != null && trip.getEndDate() != null) {
            if (trip.getEndDate().isBefore(trip.getStartDate())) {
                throw new RuntimeException("End date cannot be before start date.");
            }
        }

        Trip savedTrip = tripRepository.save(trip);
        return mapToDto(savedTrip);
    }

    @Override
    public List<TripDto> getTripsByCustomer(Long customerId) {
        return tripRepository.findByCustomerUserId(customerId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TripDto getTripById(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with ID: " + tripId));
        return mapToDto(trip);
    }

    @Override
    public TripDto updateTrip(Long tripId, TripDto dto) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        trip.setTripName(dto.getTripName());
        trip.setBudget(dto.getBudget());
        trip.setTripStatus(dto.getTripStatus());
        trip.setStartDate(dto.getStartDate());
        trip.setEndDate(dto.getEndDate());
        trip.setPackageTier(dto.getPackageTier());

        return mapToDto(tripRepository.save(trip));
    }

    @Override
    public void deleteTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Cannot delete: Trip not found with ID: " + tripId));
        try {
            tripRepository.delete(trip);
        } catch (Exception e) {
            throw new RuntimeException("Database error during deletion: " + e.getMessage());
        }
    }

    @Override
    public void addDestination(Long tripId, DestinationDto destDto) {
    }

    private TripDto mapToDto(Trip trip) {
        TripDto dto = new TripDto();
        dto.setTripId(trip.getTripId());
        dto.setTripName(trip.getTripName());
        dto.setBudget(trip.getBudget());
        dto.setStartDate(trip.getStartDate());
        dto.setEndDate(trip.getEndDate());
        dto.setTripStatus(trip.getTripStatus());
        dto.setPackageTier(trip.getPackageTier());

        boolean isPaid = (trip.getBooking() != null && trip.getBooking().getBookingsStatus() == BookingStatus.CONFIRMED)
                || (trip.getTripStatus() == TripStatus.CONFIRMED);
        dto.setPaid(isPaid);

        if (trip.getBooking() != null) {
            dto.setBookingId(trip.getBooking().getBookingId());
        }

        if (trip.getCustomer() != null) {
            dto.setCustomerId(trip.getCustomer().getUserId());
        }

        if (trip.getSelectedPackage() != null) {
            dto.setPackageName(trip.getSelectedPackage().getPackageName());
        } else {
            dto.setPackageName("Custom Trip");
        }
        return dto;
    }
}