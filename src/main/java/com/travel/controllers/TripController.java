package com.travel.controllers;

import com.travel.dtos.TripDto;
import com.travel.entities.Trip;
import com.travel.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping("/{customerId}")
    public ResponseEntity<?> createTrip(
            @PathVariable Long customerId,
            @RequestParam(required = false) Long packageId,
            @RequestBody Trip trip) {
        try {
            TripDto createdTrip = tripService.createTrip(trip, customerId, packageId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTrip);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getTripsByCustomer(@PathVariable Long customerId) {
        try {
            return ResponseEntity.ok(tripService.getTripsByCustomer(customerId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrip(@PathVariable Long id) {
        try {
            tripService.deleteTrip(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}