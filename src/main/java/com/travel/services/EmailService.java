package com.travel.services;

import com.travel.entities.Bookings;
import com.travel.entities.User;

public interface EmailService {
    void sendLoginNotification(User user);

    void sendBookingConfirmation(User user, Bookings booking, byte[] invoicePdf);

    void sendWelcomeEmail(User user);

    void sendPaymentOtp(String email, String otp);
}
