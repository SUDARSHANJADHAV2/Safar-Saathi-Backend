package com.travel.dtos;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long userId;
    private Long packageId;
    private Double amount;
    private String tier;
}
