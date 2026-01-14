package com.sajiannusantara.restaurant_api.controller;

import com.sajiannusantara.restaurant_api.model.Reservation;
import com.sajiannusantara.restaurant_api.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // Get authenticated user email from SecurityContext
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            return authentication.getName(); // This returns the email from JWT token
        }
        throw new RuntimeException("User not authenticated");
    }

    @PostMapping("/check-availability")
    public ResponseEntity<?> checkAvailability(@RequestBody Map<String, Object> request) {
        try {
            String dateStr = (String) request.get("date");
            String timeStr = (String) request.get("time");
            Integer numberOfPax = Integer.parseInt(request.get("numberOfPax").toString());

            LocalDate date = LocalDate.parse(dateStr);
            LocalTime time = LocalTime.parse(timeStr);

            List<Integer> availableTables = reservationService.getAvailableTables(date, time, numberOfPax);

            Map<String, Object> response = new HashMap<>();
            response.put("availableTables", availableTables);
            response.put("date", dateStr);
            response.put("time", timeStr);
            response.put("numberOfPax", numberOfPax);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookTable(@RequestBody Map<String, Object> request) {
        try {
            String userEmail = getCurrentUserEmail();

            Reservation reservation = new Reservation();
            reservation.setTableNumber(Integer.parseInt(request.get("tableNumber").toString()));
            reservation.setDate(LocalDate.parse((String) request.get("date")));
            reservation.setTime(LocalTime.parse((String) request.get("time")));
            reservation.setNumberOfPax(Integer.parseInt(request.get("numberOfPax").toString()));

            // Optional fields - use from user if not provided
            if (request.containsKey("customerName")) {
                reservation.setCustomerName((String) request.get("customerName"));
            }
            if (request.containsKey("phoneNumber")) {
                reservation.setPhoneNumber((String) request.get("phoneNumber"));
            }

            Reservation createdReservation = reservationService.createReservation(userEmail, reservation);
            createdReservation.setUserId(null); // Don't expose internal userId

            return ResponseEntity.ok(createdReservation);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/my-reservations")
    public ResponseEntity<?> getMyReservations() {
        try {
            String userEmail = getCurrentUserEmail();
            List<Reservation> reservations = reservationService.getUserReservations(userEmail);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}

