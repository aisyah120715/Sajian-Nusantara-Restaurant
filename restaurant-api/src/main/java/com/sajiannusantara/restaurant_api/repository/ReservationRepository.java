package com.sajiannusantara.restaurant_api.repository;

import com.sajiannusantara.restaurant_api.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
    
    // Find all reservations for a specific date and time
    List<Reservation> findByDateAndTime(LocalDate date, LocalTime time);
    
    // Find all reservations for a specific date
    List<Reservation> findByDate(LocalDate date);
    
    // Find reservations for a specific table, date, and time
    Reservation findByTableNumberAndDateAndTime(Integer tableNumber, LocalDate date, LocalTime time);
    
    // Find all reservations for a specific table on a date
    List<Reservation> findByTableNumberAndDate(Integer tableNumber, LocalDate date);
    
    // Find all reservations for a user
    List<Reservation> findByUserId(String userId);
}

