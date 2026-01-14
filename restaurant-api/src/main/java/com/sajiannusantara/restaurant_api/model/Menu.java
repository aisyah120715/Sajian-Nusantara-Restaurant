package com.sajiannusantara.restaurant_api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Menu entity representing a menu item in the restaurant
 * Images are stored in MongoDB GridFS and referenced by imageId
 */
@Data
@Document(collection = "menus")
public class Menu {

    @Id
    private String id;

    private String name;

    private String description;

    private BigDecimal price;

    private String category; // e.g., "Appetizer", "Main Course", "Dessert", "Beverage"

    /**
     * GridFS file ID for the menu item image
     * This references the image stored in MongoDB GridFS
     */
    private String imageId;

    /**
     * Timestamp when the menu item was created
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp when the menu item was last updated
     */
    private LocalDateTime updatedAt;

    /**
     * Whether the menu item is currently available
     */
    private Boolean available = true;

}


