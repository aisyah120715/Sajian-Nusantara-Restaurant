package com.sajiannusantara.restaurant_api.controller;

import com.sajiannusantara.restaurant_api.dto.MenuResponse;
import com.sajiannusantara.restaurant_api.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Public controller for menu items
 * No authentication required - for customer view
 */
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    /**
     * Get all available menu items (public endpoint)
     * GET /api/menu
     */
    @GetMapping
    public ResponseEntity<List<MenuResponse>> getAllAvailableMenus() {
        try {
            List<MenuResponse> menus = menuService.getAllMenus();
            // Filter to show only available items for public view
            List<MenuResponse> availableMenus = menus.stream()
                    .filter(menu -> menu.getAvailable() != null && menu.getAvailable())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(availableMenus);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get menu items by category (public endpoint)
     * GET /api/menu/category/{category}
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuResponse>> getMenusByCategory(@PathVariable String category) {
        try {
            List<MenuResponse> menus = menuService.getAllMenus();
            // Filter by category and availability
            List<MenuResponse> filteredMenus = menus.stream()
                    .filter(menu -> menu.getCategory() != null && 
                                   menu.getCategory().equalsIgnoreCase(category) &&
                                   menu.getAvailable() != null && 
                                   menu.getAvailable())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(filteredMenus);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

