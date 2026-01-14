package com.sajiannusantara.restaurant_api.service;

import com.sajiannusantara.restaurant_api.model.Order;
import com.sajiannusantara.restaurant_api.model.User;
import com.sajiannusantara.restaurant_api.repository.OrderRepository;
import com.sajiannusantara.restaurant_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public Order createOrder(String userEmail, Order order) {
        // Get user from email
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set user ID
        order.setUserId(user.getId());

        // Set status to PENDING
        order.setStatus("PENDING");

        // Set creation timestamp
        order.setCreatedAt(LocalDateTime.now());

        // Generate order number if not provided
        if (order.getOrderNumber() == null || order.getOrderNumber().isEmpty()) {
            order.setOrderNumber(String.valueOf(Math.floor(Math.random() * 9000) + 1000));
        }

        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUserId(user.getId());
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public Order updateOrderStatus(String id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}

