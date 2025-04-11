package com.travel.dtos;

import lombok.Data;

@Data
public class PackageDto {
    private Long packageId;
    private String packageName;
    private String description;
    private Double price;
    private String imageUrl;
    private Long vendorId;
    private String vendorName;
    private String highlights;
    private String restaurants;
}