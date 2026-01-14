package com.sajiannusantara.restaurant_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@restaurant.com}")
    private String fromEmail;

    @Value("${app.frontend.url:http://localhost:5173}")
    private String frontendUrl;

    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Password Reset Request - Sajian Nusantara");
            
            String resetLink = frontendUrl + "/reset-password?token=" + resetToken;
            
            message.setText(
                "Hello,\n\n" +
                "You requested to reset your password for your Sajian Nusantara account.\n\n" +
                "Click the link below to reset your password:\n" +
                resetLink + "\n\n" +
                "This link will expire in 1 hour.\n\n" +
                "If you didn't request this, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Sajian Nusantara Team"
            );

            mailSender.send(message);
            System.out.println("Password reset email sent to: " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            // Don't throw exception - log it but don't fail the request
            // In production, you might want to queue the email for retry
        }
    }

    public void sendReservationConfirmationEmail(String toEmail, String customerName, String date, String time, int numberOfGuests, String tableNumber) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Reservation Confirmed - Sajian Nusantara");
            
            message.setText(
                "Dear " + customerName + ",\n\n" +
                "We are pleased to confirm your table reservation at Sajian Nusantara!\n\n" +
                "Reservation Details:\n" +
                "Date: " + date + "\n" +
                "Time: " + time + "\n" +
                "Number of Guests: " + numberOfGuests + "\n" +
                "Table Number: " + (tableNumber != null ? tableNumber : "To be assigned") + "\n\n" +
                "We look forward to serving you and providing you with an authentic Malaysian dining experience.\n\n" +
                "If you need to make any changes or have any questions, please contact us.\n\n" +
                "Thank you for choosing Sajian Nusantara!\n\n" +
                "Best regards,\n" +
                "Sajian Nusantara Team"
            );

            mailSender.send(message);
            System.out.println("Reservation confirmation email sent to: " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send reservation confirmation email: " + e.getMessage());
            // Don't throw exception - log it but don't fail the request
        }
    }

    public void sendReservationCancellationEmail(String toEmail, String customerName, String date, String time, int numberOfGuests, String tableNumber) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Reservation Cancelled - Sajian Nusantara");
            
            message.setText(
                "Dear " + customerName + ",\n\n" +
                "We regret to inform you that your table reservation at Sajian Nusantara has been cancelled.\n\n" +
                "Cancelled Reservation Details:\n" +
                "Date: " + date + "\n" +
                "Time: " + time + "\n" +
                "Number of Guests: " + numberOfGuests + "\n" +
                "Table Number: " + (tableNumber != null ? tableNumber : "N/A") + "\n\n" +
                "We apologize for any inconvenience this may cause. If you would like to make a new reservation, please feel free to contact us or visit our website.\n\n" +
                "If you have any questions or concerns, please don't hesitate to reach out to us.\n\n" +
                "Thank you for your understanding.\n\n" +
                "Best regards,\n" +
                "Sajian Nusantara Team"
            );

            mailSender.send(message);
            System.out.println("Reservation cancellation email sent to: " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send reservation cancellation email: " + e.getMessage());
            // Don't throw exception - log it but don't fail the request
        }
    }

}

