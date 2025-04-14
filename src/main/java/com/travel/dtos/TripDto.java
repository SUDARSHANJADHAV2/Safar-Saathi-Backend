package com.travel.dtos;

import java.time.LocalDate;
import com.travel.entities.TripStatus;
import com.travel.entities.PackageTier;
import lombok.Data;

@Data
public class TripDto {
    private Long tripId;
    private String tripName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double budget;
    private TripStatus tripStatus;
    private PackageTier packageTier;

    private Long customerId;
    private String packageName;
    private boolean paid;
    private Long bookingId;
}