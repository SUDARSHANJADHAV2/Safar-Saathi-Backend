package com.travel.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private String razorpayOrderId;
    private String currency;
    private Integer amount;
    private String keyId;
}
