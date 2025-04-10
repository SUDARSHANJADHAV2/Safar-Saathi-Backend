package com.travel.services;

import com.travel.dtos.PaymentRequest;
import com.travel.dtos.PaymentResponse;
import java.util.Map;

public interface PaymentService {
    PaymentResponse createOrder(PaymentRequest request) throws Exception;

    boolean verifyPayment(Map<String, String> data) throws Exception;
}
