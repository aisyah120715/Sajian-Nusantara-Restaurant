package com.sajiannusantara.restaurant_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for dashboard statistics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {
    private Long totalUsers;
    private Long totalReservations;
    private Long totalMenuItems;
    private Double totalRevenue; // Optional revenue calculation
    private Double todayRevenue; // Today's revenue from orders
    private Long todayOrderCount; // Total orders for today
}

