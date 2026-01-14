package com.sajiannusantara.restaurant_api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Document(collection = "reservations")
public class Reservation {

    @Id
    private String id;

    private String customerName; // Name of the customer who made the reservation
    
    private String phoneNumber; // Phone number of the customer
    
    private Integer tableNumber; // Table number (1-15)
    
    private LocalDate date; // Reservation date
    
    private LocalTime time; // Reservation time
    
    private Integer numberOfPax; // Number of people
    
    private String userId; // ID of the user who made the reservation (from JWT)

    /**
     * Reservation status: PENDING, CONFIRMED, CANCELLED
     */
    private String status = "PENDING";

}

