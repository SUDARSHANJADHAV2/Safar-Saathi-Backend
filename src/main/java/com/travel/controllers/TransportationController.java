package com.travel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.travel.entities.Transportation;
import com.travel.services.TransportationService;

@RestController
@RequestMapping("/api/transportation")
public class TransportationController {

    @Autowired
    private TransportationService transportService;

    @PostMapping("/{tripId}")
    public ResponseEntity<?> addTransport(@RequestBody Transportation transport, @PathVariable Long tripId) {
        try {
            return ResponseEntity.ok(transportService.addTransport(transport, tripId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}