package com.sajiannusantara.restaurant_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for menu item responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String imageId;
    private String imageUrl; // URL to retrieve the image
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean available;
}

