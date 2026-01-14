package com.sajiannusantara.restaurant_api.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO for creating/updating menu items
 * Used for JSON request body (without image)
 */
@Data
public class MenuRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Boolean available;
}

