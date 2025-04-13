package com.travel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.travel.dtos.PackageDto;
import com.travel.entities.Packages;
import com.travel.services.PackageService;

@RestController
@RequestMapping("/api/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @PostMapping("/{vendorId}")
    public ResponseEntity<?> createPackage(@RequestBody Packages pkg, @PathVariable Long vendorId) {
        try {
            PackageDto newPkg = packageService.createPackage(pkg, vendorId);
            return ResponseEntity.ok(newPkg);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPackages() {
        try {
            return ResponseEntity.ok(packageService.getAllPackages());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePackage(@PathVariable Long id) {
        try {
            packageService.deletePackage(id);
            return ResponseEntity.ok("Package deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPackageById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(packageService.getPackageById(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPackages(@RequestParam String query) {
        try {
            return ResponseEntity.ok(packageService.searchPackages(query));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}