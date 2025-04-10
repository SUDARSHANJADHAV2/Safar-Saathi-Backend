package com.travel.services.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import com.travel.dtos.PaymentRequest;
import com.travel.dtos.PaymentResponse;
import com.travel.entities.*;
import com.travel.repositories.*;
import com.travel.services.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PackagesRepository packagesRepository;

    @Autowired
    private com.travel.services.PostPaymentService postPaymentService;

    @Override
    public PaymentResponse createOrder(PaymentRequest request) throws Exception {
        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int) (request.getAmount() * 100));
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

        Order order = client.orders.create(orderRequest);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Payment payment = Payment.builder()
                .razorpayOrderId(order.get("id"))
                .amount(request.getAmount())
                .currency("INR")
                .status(PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        paymentRepository.save(payment);

        return PaymentResponse.builder()
                .razorpayOrderId(order.get("id"))
                .amount(order.get("amount"))
                .currency(order.get("currency"))
                .keyId(keyId)
                .build();
    }

    @Override
    public boolean verifyPayment(Map<String, String> data) throws Exception {
        String orderId = data.get("razorpay_order_id");
        String paymentId = data.get("razorpay_payment_id");
        String signature = data.get("razorpay_signature");

        String tripIdStr = data.get("tripId");
        if (tripIdStr == null || tripIdStr.isEmpty() || tripIdStr.equals("undefined")) {
            throw new RuntimeException("Trip ID is missing. Cannot verify payment.");
        }
        Long tripId = Long.parseLong(tripIdStr);

        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", orderId);
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_signature", signature);

        boolean isValid = Utils.verifyPaymentSignature(options, keySecret);

        if (isValid) {
            Payment payment = paymentRepository.findByRazorpayOrderId(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

            payment.setRazorpayPaymentId(paymentId);
            payment.setRazorpaySignature(signature);
            payment.setStatus(PaymentStatus.COMPLETED);

            Trip trip = tripRepository.findById(tripId)
                    .orElseThrow(() -> new RuntimeException("Trip not found with ID: " + tripId));

            Bookings booking = new Bookings();
            booking.setTrip(trip);
            booking.setBookingDate(LocalDate.now());
            booking.setBookingsStatus(BookingStatus.CONFIRMED);

            Bookings savedBooking = bookingRepository.save(booking);

            payment.setBooking(savedBooking);
            trip.setTripStatus(TripStatus.CONFIRMED);
            tripRepository.save(trip);
            paymentRepository.save(payment);

            try {
                postPaymentService.sendPostPaymentNotificationAsync(savedBooking);
            } catch (Exception e) {
            }

            return true;
        } else {
            Payment payment = paymentRepository.findByRazorpayOrderId(orderId).orElse(null);
            if (payment != null) {
                payment.setStatus(PaymentStatus.FAILED);
                paymentRepository.save(payment);
            }
            return false;
        }
    }
}
