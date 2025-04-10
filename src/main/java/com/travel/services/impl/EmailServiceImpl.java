package com.travel.services.impl;

import com.travel.entities.Bookings;
import com.travel.entities.User;
import com.travel.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    @Override
    public void sendLoginNotification(User user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail, "SafarSaathi");
            helper.setTo(user.getEmail());
            helper.setSubject("Security Alert: Successful Login to SafarSaathi");

            String content = "<html>" +
                    "<body style='font-family: Arial, sans-serif; color: #333;'>" +
                    "<div style='max-width: 600px; margin: 0 auto; border: 1px solid #ddd; padding: 20px; border-radius: 8px;'>"
                    +
                    "<h2 style='color: #2c3e50;'>Hello " + user.getName() + ",</h2>" +
                    "<p>We noticed a successful login to your <strong>SafarSaathi</strong> account.</p>" +
                    "<p style='background: #f9f9f9; padding: 10px; border-left: 4px solid #3498db;'>" +
                    "<strong>Date:</strong> " + java.time.LocalDateTime.now() + "<br>" +
                    "<strong>Account:</strong> " + user.getEmail() + "</p>" +
                    "<p>If this was you, you can safely ignore this email. If not, please secure your account immediately.</p>"
                    +
                    "<br><p>Best regards,<br>Team SafarSaathi</p>" +
                    "</div></body></html>";

            helper.setText(content, true);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("FAILED to send login email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void sendBookingConfirmation(User user, Bookings booking, byte[] invoicePdf) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail, "SafarSaathi");
            helper.setTo(user.getEmail());
            helper.setSubject("Booking Confirmed! - Your SafarSaathi Adventure Awaits");

            String content = "<html>" +
                    "<body style='font-family: Arial, sans-serif; color: #333;'>" +
                    "<div style='max-width: 600px; margin: 0 auto; border: 1px solid #ddd; padding: 20px; border-radius: 8px;'>"
                    +
                    "<h1 style='color: #27ae60; text-align: center;'>Pack Your Bags!</h1>" +
                    "<h2>Hi " + user.getName() + ",</h2>" +
                    "<p>Your booking with <strong>SafarSaathi</strong> is confirmed.</p>" +
                    "<div style='background: #f1f8e9; padding: 15px; border-radius: 5px;'>" +
                    "<h3>Trip Details:</h3>" +
                    "<p><strong>Package:</strong> " + booking.getTrip().getSelectedPackage().getPackageName() + "</p>" +
                    "<p><strong>Travel Date:</strong> " + booking.getTrip().getStartDate() + "</p>" +
                    "</div>" +
                    "<p>Please find your payment invoice attached to this email.</p>" +
                    "<br><p>Happy Journey!<br>Team SafarSaathi</p>" +
                    "</div></body></html>";

            helper.setText(content, true);
            helper.addAttachment("Invoice_" + booking.getBookingId() + ".pdf", new ByteArrayResource(invoicePdf));

            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("FAILED to send booking email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void sendWelcomeEmail(User user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail, "SafarSaathi");
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to SafarSaathi!");

            String content = "<html>" +
                    "<body style='font-family: Arial, sans-serif; color: #333;'>" +
                    "<div style='max-width: 600px; margin: 0 auto; border: 1px solid #ddd; padding: 20px; border-radius: 8px;'>"
                    +
                    "<h1 style='color: #2c3e50; text-align: center;'>Welcome Aboard!</h1>" +
                    "<h2>Hi " + user.getName() + ",</h2>" +
                    "<p>Thank you for joining <strong>SafarSaathi</strong>. We're excited to help you plan your next adventure!</p>"
                    +
                    "<p>Feel free to explore our packages and start booking your dream trips.</p>" +
                    "<br><p>Happy Travels!<br>Team SafarSaathi</p>" +
                    "</div></body></html>";

            helper.setText(content, true);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("FAILED to send welcome email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void sendPaymentOtp(String email, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail, "SafarSaathi Security");
            helper.setTo(email);
            helper.setSubject("Payment Verification OTP - SafarSaathi");

            String content = "<html>" +
                    "<body style='font-family: Arial, sans-serif; color: #333;'>" +
                    "<div style='max-width: 600px; margin: 0 auto; border: 1px solid #ddd; padding: 20px; border-radius: 8px; text-align: center;'>"
                    +
                    "<h2 style='color: #2c3e50;'>Payment Verification</h2>" +
                    "<p>Use the following One-Time Password (OTP) to complete your booking verification.</p>" +
                    "<div style='background: #f1f8e9; padding: 20px; margin: 20px 0; border-radius: 8px;'>" +
                    "<span style='font-size: 32px; font-weight: bold; letter-spacing: 5px; color: #27ae60;'>" + otp
                    + "</span>" +
                    "</div>" +
                    "<p>This OTP is valid for 10 minutes. Do not share this code with anyone.</p>" +
                    "<br><p style='font-size: 12px; color: #777;'>If you did not initiate this booking, please contact support immediately.</p>"
                    +
                    "</div></body></html>";

            helper.setText(content, true);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("FAILED to send payment OTP: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
