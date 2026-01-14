package com.sajiannusantara.restaurant_api.service;

import com.sajiannusantara.restaurant_api.model.Reservation;
import com.sajiannusantara.restaurant_api.model.User;
import com.sajiannusantara.restaurant_api.repository.ReservationRepository;
import com.sajiannusantara.restaurant_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    // Get list of available table numbers (1-15)
    public List<Integer> getAvailableTables(LocalDate date, LocalTime time, Integer numberOfPax) {
        List<Integer> allTables = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            allTables.add(i);
        }

        // Get all reservations for the given date and time
        List<Reservation> reservations = reservationRepository.findByDateAndTime(date, time);

        // Filter out occupied tables
        List<Integer> occupiedTables = reservations.stream()
                .map(Reservation::getTableNumber)
                .toList();

        // Return available tables (not in occupied list)
        return allTables.stream()
                .filter(table -> !occupiedTables.contains(table))
                .toList();
    }

    public Reservation createReservation(String userEmail, Reservation reservation) {
        // Get user from email
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if table is available for the given date and time
        Reservation existingReservation = reservationRepository
                .findByTableNumberAndDateAndTime(
                        reservation.getTableNumber(),
                        reservation.getDate(),
                        reservation.getTime()
                );

        if (existingReservation != null) {
            throw new RuntimeException("Table " + reservation.getTableNumber() + " is already booked for this date and time");
        }

        // Validate table number (1-15)
        if (reservation.getTableNumber() < 1 || reservation.getTableNumber() > 15) {
            throw new RuntimeException("Invalid table number. Table number must be between 1 and 15");
        }

        // Set customer name from user's username if not provided
        if (reservation.getCustomerName() == null || reservation.getCustomerName().isEmpty()) {
            reservation.setCustomerName(user.getUsername());
        }

        // Set phone number from user if not provided
        if (reservation.getPhoneNumber() == null || reservation.getPhoneNumber().isEmpty()) {
            reservation.setPhoneNumber(user.getPhoneNumber());
        }

        // Set userId
        reservation.setUserId(user.getId());

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getUserReservations(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reservationRepository.findByUserId(user.getId());
    }
}

