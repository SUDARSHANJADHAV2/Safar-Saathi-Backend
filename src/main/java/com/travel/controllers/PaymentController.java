package com.travel.controllers;

import com.travel.dtos.PaymentRequest;
import com.travel.dtos.PaymentResponse;
import com.travel.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import com.travel.services.EmailService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private com.travel.repositories.BookingRepository bookingRepository;

    @Autowired
    private com.travel.utils.PdfInvoiceGenerator pdfInvoiceGenerator;

    @Autowired
    private EmailService emailService;

    private Map<String, String> otpStorage = new ConcurrentHashMap<>();

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(email, otp);

        new Thread(() -> emailService.sendPaymentOtp(email, otp)).start();

        return ResponseEntity.ok("OTP sent to " + email);
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateOtp(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String otp = data.get("otp");

        if (otpStorage.containsKey(email) && otpStorage.get(email).equals(otp)) {
            otpStorage.remove(email);
            return ResponseEntity.ok(Map.of("status", "success", "message", "OTP Verified"));
        }
        return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Invalid OTP"));
    }

    @GetMapping("/invoice/{bookingId}")
    public ResponseEntity<?> downloadInvoice(@PathVariable Long bookingId) {
        try {
            com.travel.entities.Bookings booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

            byte[] pdfContent = pdfInvoiceGenerator.generateInvoice(booking);

            return ResponseEntity.ok()
                    .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=invoice_" + bookingId + ".pdf")
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(pdfContent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error generating invoice: " + e.getMessage());
        }
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody PaymentRequest request) {
        try {
            PaymentResponse response = paymentService.createOrder(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage() != null ? e.getMessage() : "Unknown error during order creation"));
        }
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> data) {
        try {
            boolean isValid = paymentService.verifyPayment(data);
            if (isValid) {
                return ResponseEntity.ok(Map.of("status", "success", "message", "Payment verified successfully"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Invalid signature"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Error during verification"));
        }
    }
}
