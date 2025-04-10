package com.travel.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dtos.PackageDto;
import com.travel.entities.Packages;
import com.travel.entities.User;
import com.travel.entities.UserRole;
import com.travel.repositories.PackagesRepository;
import com.travel.repositories.UserRepository;
import com.travel.services.PackageService;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackagesRepository packageRepo;

    @Autowired
    private UserRepository userRepo;

    private PackageDto mapToDto(Packages pkg) {
        PackageDto dto = new PackageDto();
        dto.setPackageId(pkg.getPackageId());
        dto.setPackageName(pkg.getPackageName());
        dto.setDescription(pkg.getDescription());
        dto.setPrice(pkg.getPrice());
        dto.setImageUrl(pkg.getImageUrl());
        dto.setHighlights(pkg.getHighlights());
        dto.setRestaurants(pkg.getRestaurants());

        if (pkg.getVendor() != null) {
            dto.setVendorId(pkg.getVendor().getUserId());
            dto.setVendorName(pkg.getVendor().getName());
        }
        return dto;
    }

    @Override
    public PackageDto createPackage(Packages pkg, Long vendorId) {

        User vendor = userRepo.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with ID: " + vendorId));

        if (vendor.getUserRole() != UserRole.VENDOR) {
            throw new RuntimeException("Access Denied: Only users with VENDOR role can create packages. Current role: "
                    + vendor.getUserRole());
        }

        pkg.setVendor(vendor);
        Packages savedPkg = packageRepo.save(pkg);

        return mapToDto(savedPkg);
    }

    @Override
    public List<PackageDto> getAllPackages() {
        return packageRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePackage(Long id) {
        if (packageRepo.existsById(id)) {
            packageRepo.deleteById(id);
        } else {
            throw new RuntimeException("Package not found with id: " + id);
        }
    }

    @Override
    public PackageDto getPackageById(Long id) {
        Packages pkg = packageRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));
        return mapToDto(pkg);
    }

    @Override
    public List<PackageDto> searchPackages(String query) {
        return packageRepo.findByPackageNameContainingIgnoreCase(query)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}