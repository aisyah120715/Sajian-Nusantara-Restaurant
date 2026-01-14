package com.sajiannusantara.restaurant_api.controller;

import com.sajiannusantara.restaurant_api.dto.MenuRequest;
import com.sajiannusantara.restaurant_api.dto.MenuResponse;
import com.sajiannusantara.restaurant_api.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Admin controller for menu management
 * All endpoints require ADMIN role
 */
@RestController
@RequestMapping("/api/admin/menus")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminMenuController {

    private final MenuService menuService;

    /**
     * Create a new menu item with image
     * POST /api/admin/menus
     * Content-Type: multipart/form-data
     * 
     * Form fields:
     * - name: String
     * - description: String
     * - price: BigDecimal (as string)
     * - category: String
     * - available: Boolean (optional, default true)
     * - image: MultipartFile (optional)
     */
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<MenuResponse> createMenu(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            @RequestParam("category") String category,
            @RequestParam(value = "available", required = false, defaultValue = "true") Boolean available,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        try {
            MenuRequest menuRequest = new MenuRequest();
            menuRequest.setName(name);
            menuRequest.setDescription(description);
            menuRequest.setPrice(new java.math.BigDecimal(price));
            menuRequest.setCategory(category);
            menuRequest.setAvailable(available);
            
            MenuResponse response = menuService.createMenu(menuRequest, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (MaxUploadSizeExceededException e) {
            e.printStackTrace();
            System.err.println("MaxUploadSizeExceededException in createMenu: " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "File size exceeds maximum allowed size (50MB). Please use a smaller image.");
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(null);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IOException in createMenu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("NumberFormatException in createMenu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception in createMenu: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Test endpoint to verify multipart is working
     * POST /api/admin/menus/test
     */
    @PostMapping(value = "/test", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> testMultipart(
            @RequestParam(value = "test", required = false) String test,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("test", test);
        response.put("fileReceived", file != null && !file.isEmpty());
        if (file != null && !file.isEmpty()) {
            response.put("fileName", file.getOriginalFilename());
            response.put("fileSize", file.getSize());
        }
        return ResponseEntity.ok(response);
    }

    /**
     * Get all menu items
     * GET /api/admin/menus
     */
    @GetMapping
    public ResponseEntity<List<MenuResponse>> getAllMenus() {
        try {
            List<MenuResponse> menus = menuService.getAllMenus();
            return ResponseEntity.ok(menus);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in getAllMenus: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get menu item by ID
     * GET /api/admin/menus/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuResponse> getMenuById(@PathVariable String id) {
        try {
            MenuResponse menu = menuService.getMenuById(id);
            return ResponseEntity.ok(menu);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update menu item
     * PUT /api/admin/menus/{id}
     * Content-Type: multipart/form-data
     * 
     * Form fields (all optional):
     * - name: String
     * - description: String
     * - price: BigDecimal (as string)
     * - category: String
     * - available: Boolean
     * - image: MultipartFile
     */
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<MenuResponse> updateMenu(
            @PathVariable String id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "available", required = false) Boolean available,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        try {
            MenuRequest menuRequest = new MenuRequest();
            menuRequest.setName(name);
            menuRequest.setDescription(description);
            if (price != null) {
                menuRequest.setPrice(new java.math.BigDecimal(price));
            }
            menuRequest.setCategory(category);
            menuRequest.setAvailable(available);
            
            MenuResponse response = menuService.updateMenu(id, menuRequest, image);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Delete menu item
     * DELETE /api/admin/menus/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable String id) {
        try {
            menuService.deleteMenu(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

