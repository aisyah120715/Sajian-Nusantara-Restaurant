package com.sajiannusantara.restaurant_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for reservation responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private String id;
    private String userId;
    private String name; // customerName
    private String phone; // phoneNumber
    private LocalDate date;
    private LocalTime time;
    private Integer numberOfGuests; // numberOfPax
    private String status;
    private Integer tableNumber;
}

