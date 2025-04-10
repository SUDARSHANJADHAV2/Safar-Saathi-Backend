package com.travel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.travel.entities.Destinations;
import com.travel.services.DestinationsService;

@RestController
@RequestMapping("/api/destinations")
public class DestinationsController {

    @Autowired
    private DestinationsService destService;

    @PostMapping("/{tripId}")
    public ResponseEntity<?> addDestination(@RequestBody Destinations dest, @PathVariable Long tripId) {
        try {
            return ResponseEntity.ok(destService.addDestination(dest, tripId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}