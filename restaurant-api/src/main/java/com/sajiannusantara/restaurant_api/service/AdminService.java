package com.sajiannusantara.restaurant_api.service;

import com.sajiannusantara.restaurant_api.dto.DashboardStatsResponse;
import com.sajiannusantara.restaurant_api.dto.ReservationResponse;
import com.sajiannusantara.restaurant_api.dto.UserResponse;
import com.sajiannusantara.restaurant_api.model.Order;
import com.sajiannusantara.restaurant_api.model.Reservation;
import com.sajiannusantara.restaurant_api.model.User;
import com.sajiannusantara.restaurant_api.repository.MenuRepository;
import com.sajiannusantara.restaurant_api.repository.OrderRepository;
import com.sajiannusantara.restaurant_api.repository.ReservationRepository;
import com.sajiannusantara.restaurant_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for admin operations
 * Handles user management, reservation management, and dashboard statistics
 */
@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;
    private final EmailService emailService;

    // ==================== USER MANAGEMENT ====================

    /**
     * Get all users
     */
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get user by ID
     */
    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapToUserResponse(user);
    }

    /**
     * Update user role
     */
    public UserResponse updateUserRole(String id, String newRole) {
        if (!newRole.equals("USER") && !newRole.equals("ADMIN")) {
            throw new RuntimeException("Invalid role. Must be USER or ADMIN");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setRole(newRole);
        User updatedUser = userRepository.save(user);
        return mapToUserResponse(updatedUser);
    }

    /**
     * Delete user
     */
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Map User entity to UserResponse DTO
     */
    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }

    // ==================== RESERVATION MANAGEMENT ====================

    /**
     * Get all reservations
     */
    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::mapToReservationResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get reservation by ID
     */
    public ReservationResponse getReservationById(String id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        return mapToReservationResponse(reservation);
    }

    /**
     * Update reservation status
     */
    public ReservationResponse updateReservationStatus(String id, String status) {
        if (!status.equals("PENDING") && !status.equals("CONFIRMED") && !status.equals("CANCELLED")) {
            throw new RuntimeException("Invalid status. Must be PENDING, CONFIRMED, or CANCELLED");
        }

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        reservation.setStatus(status);
        Reservation updatedReservation = reservationRepository.save(reservation);
        return mapToReservationResponse(updatedReservation);
    }

    /**
     * Confirm reservation and send confirmation email
     */
    public ReservationResponse confirmReservation(String id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        // Check if reservation is already confirmed
        if ("CONFIRMED".equals(reservation.getStatus())) {
            throw new RuntimeException("Reservation is already confirmed");
        }

        // Update status to CONFIRMED
        reservation.setStatus("CONFIRMED");
        Reservation updatedReservation = reservationRepository.save(reservation);

        // Send confirmation email if user exists
        if (reservation.getUserId() != null) {
            try {
                User user = userRepository.findById(reservation.getUserId()).orElse(null);
                if (user != null && user.getEmail() != null) {
                    // Format date as "Jan 12, 2026"
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
                    String dateStr = reservation.getDate().format(dateFormatter);
                    
                    // Format time as "9:30 AM"
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
                    String timeStr = reservation.getTime().format(timeFormatter);
                    
                    String tableNumberStr = reservation.getTableNumber() != null 
                        ? reservation.getTableNumber().toString() 
                        : null;
                    
                    emailService.sendReservationConfirmationEmail(
                        user.getEmail(),
                        reservation.getCustomerName(),
                        dateStr,
                        timeStr,
                        reservation.getNumberOfPax(),
                        tableNumberStr
                    );
                }
            } catch (Exception e) {
                // Log error but don't fail the confirmation
                System.err.println("Failed to send confirmation email: " + e.getMessage());
            }
        }

        return mapToReservationResponse(updatedReservation);
    }

    /**
     * Cancel reservation and send cancellation email
     */
    public ReservationResponse cancelReservation(String id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        // Check if reservation is already cancelled
        if ("CANCELLED".equals(reservation.getStatus())) {
            throw new RuntimeException("Reservation is already cancelled");
        }

        // Update status to CANCELLED
        reservation.setStatus("CANCELLED");
        Reservation updatedReservation = reservationRepository.save(reservation);

        // Send cancellation email if user exists
        if (reservation.getUserId() != null) {
            try {
                User user = userRepository.findById(reservation.getUserId()).orElse(null);
                if (user != null && user.getEmail() != null) {
                    // Format date as "Jan 12, 2026"
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
                    String dateStr = reservation.getDate().format(dateFormatter);
                    
                    // Format time as "9:30 AM"
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
                    String timeStr = reservation.getTime().format(timeFormatter);
                    
                    String tableNumberStr = reservation.getTableNumber() != null 
                        ? reservation.getTableNumber().toString() 
                        : null;
                    
                    emailService.sendReservationCancellationEmail(
                        user.getEmail(),
                        reservation.getCustomerName(),
                        dateStr,
                        timeStr,
                        reservation.getNumberOfPax(),
                        tableNumberStr
                    );
                }
            } catch (Exception e) {
                // Log error but don't fail the cancellation
                System.err.println("Failed to send cancellation email: " + e.getMessage());
            }
        }

        return mapToReservationResponse(updatedReservation);
    }

    /**
     * Delete reservation
     */
    public void deleteReservation(String id) {
        if (!reservationRepository.existsById(id)) {
            throw new RuntimeException("Reservation not found with id: " + id);
        }
        reservationRepository.deleteById(id);
    }

    /**
     * Map Reservation entity to ReservationResponse DTO
     */
    private ReservationResponse mapToReservationResponse(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .userId(reservation.getUserId())
                .name(reservation.getCustomerName())
                .phone(reservation.getPhoneNumber())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .numberOfGuests(reservation.getNumberOfPax())
                .status(reservation.getStatus() != null ? reservation.getStatus() : "PENDING")
                .tableNumber(reservation.getTableNumber())
                .build();
    }

    // ==================== DASHBOARD STATISTICS ====================

    /**
     * Get dashboard statistics
     */
    public DashboardStatsResponse getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalReservations = reservationRepository.count();
        long totalMenuItems = menuRepository.count();

        // Calculate total revenue (optional - you can implement this based on your business logic)
        // For now, we'll return 0.0
        double totalRevenue = 0.0;

        // Calculate today's revenue and order count
        LocalDateTime startOfToday = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfToday = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        
        List<Order> todayOrders = orderRepository.findAll().stream()
                .filter(order -> {
                    if (order.getCreatedAt() == null) return false;
                    LocalDateTime orderDate = order.getCreatedAt();
                    return !orderDate.isBefore(startOfToday) && !orderDate.isAfter(endOfToday);
                })
                .collect(Collectors.toList());
        
        double todayRevenue = todayOrders.stream()
                .filter(order -> order.getTotal() != null)
                .mapToDouble(order -> order.getTotal())
                .sum();
        
        long todayOrderCount = todayOrders.size();

        return DashboardStatsResponse.builder()
                .totalUsers(totalUsers)
                .totalReservations(totalReservations)
                .totalMenuItems(totalMenuItems)
                .totalRevenue(totalRevenue)
                .todayRevenue(todayRevenue)
                .todayOrderCount(todayOrderCount)
                .build();
    }

}

