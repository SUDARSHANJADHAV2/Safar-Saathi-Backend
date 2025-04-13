package com.travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.entities.Packages;
import java.util.List;

public interface PackagesRepository extends JpaRepository<Packages, Long> {
    List<Packages> findByVendor_UserId(Long vendorId);

    List<Packages> findByPackageNameContainingIgnoreCase(String packageName);
}