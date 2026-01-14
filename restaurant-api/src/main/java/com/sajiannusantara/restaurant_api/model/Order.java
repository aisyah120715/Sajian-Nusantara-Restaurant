package com.sajiannusantara.restaurant_api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String userId; // ID of the user who placed the order

    private String orderNumber; // Order number (e.g., #6936)

    private String orderType; // "dine-in" or "pickup"

    private Integer tableNumber; // Table number (only for dine-in orders)

    private List<OrderItem> items; // List of ordered items

    private Double subtotal; // Subtotal before fees

    private Double serviceFee; // Service fee or packaging fee

    private Double total; // Total amount

    private String paymentMethod; // "online" or "counter"

    private String status; // "PENDING", "CONFIRMED", "COMPLETED", "CANCELLED"

    private LocalDateTime createdAt; // Order creation timestamp

    @Data
    public static class OrderItem {
        private String menuItemId; // ID of the menu item
        private String name; // Name of the menu item
        private Double price; // Price per item
        private Integer quantity; // Quantity ordered
        private String notes; // Special notes/instructions
    }
}

