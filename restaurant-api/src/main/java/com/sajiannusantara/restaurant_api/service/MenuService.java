package com.sajiannusantara.restaurant_api.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.sajiannusantara.restaurant_api.dto.MenuRequest;
import com.sajiannusantara.restaurant_api.dto.MenuResponse;
import com.sajiannusantara.restaurant_api.model.Menu;
import com.sajiannusantara.restaurant_api.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for menu management operations
 * Handles CRUD operations and GridFS image storage
 */
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MongoDatabaseFactory mongoDatabaseFactory;

    /**
     * Get GridFS bucket for image storage
     */
    private GridFSBucket getGridFSBucket() {
        return GridFSBuckets.create(mongoDatabaseFactory.getMongoDatabase());
    }

    /**
     * Store image in GridFS and return the file ID
     */
    private String storeImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            GridFSBucket gridFSBucket = getGridFSBucket();
            String filename = file.getOriginalFilename();
            if (filename == null || filename.isEmpty()) {
                filename = "menu-image-" + System.currentTimeMillis();
            }
            ObjectId fileId = gridFSBucket.uploadFromStream(
                    filename,
                    file.getInputStream()
            );
            return fileId.toString();
        } catch (Exception e) {
            System.err.println("Error storing image in GridFS: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Failed to store image: " + e.getMessage(), e);
        }
    }

    /**
     * Delete image from GridFS by file ID
     */
    private void deleteImage(String imageId) {
        if (imageId == null || imageId.isEmpty()) {
            return;
        }

        try {
            GridFSBucket gridFSBucket = getGridFSBucket();
            gridFSBucket.delete(new ObjectId(imageId));
        } catch (Exception e) {
            // Log error but don't throw - image might already be deleted
            System.err.println("Error deleting image from GridFS: " + e.getMessage());
        }
    }

    /**
     * Create a new menu item with image
     */
    public MenuResponse createMenu(MenuRequest request, MultipartFile image) throws IOException {
        try {
            Menu menu = new Menu();
            menu.setName(request.getName());
            menu.setDescription(request.getDescription());
            menu.setPrice(request.getPrice());
            menu.setCategory(request.getCategory());
            menu.setAvailable(request.getAvailable() != null ? request.getAvailable() : true);
            menu.setCreatedAt(LocalDateTime.now());
            menu.setUpdatedAt(LocalDateTime.now());

            // Store image in GridFS if provided
            if (image != null && !image.isEmpty()) {
                try {
                    String imageId = storeImage(image);
                    menu.setImageId(imageId);
                } catch (IOException e) {
                    // Log error but continue without image
                    System.err.println("Warning: Failed to store image, creating menu without image: " + e.getMessage());
                    // Continue without image - menu can be created without image
                }
            }

            Menu savedMenu = menuRepository.save(menu);
            return mapToResponse(savedMenu);
        } catch (Exception e) {
            System.err.println("Error in createMenu: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Get all menu items
     */
    public List<MenuResponse> getAllMenus() {
        return menuRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get menu item by ID
     */
    public MenuResponse getMenuById(String id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id));
        return mapToResponse(menu);
    }

    /**
     * Update menu item
     */
    public MenuResponse updateMenu(String id, MenuRequest request, MultipartFile newImage) throws IOException {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id));

        // Update fields
        if (request.getName() != null) {
            menu.setName(request.getName());
        }
        if (request.getDescription() != null) {
            menu.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            menu.setPrice(request.getPrice());
        }
        if (request.getCategory() != null) {
            menu.setCategory(request.getCategory());
        }
        if (request.getAvailable() != null) {
            menu.setAvailable(request.getAvailable());
        }
        menu.setUpdatedAt(LocalDateTime.now());

        // Handle image update
        if (newImage != null && !newImage.isEmpty()) {
            // Delete old image if exists
            if (menu.getImageId() != null) {
                deleteImage(menu.getImageId());
            }
            // Store new image
            String newImageId = storeImage(newImage);
            menu.setImageId(newImageId);
        }

        Menu updatedMenu = menuRepository.save(menu);
        return mapToResponse(updatedMenu);
    }

    /**
     * Delete menu item and its image
     */
    public void deleteMenu(String id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id));

        // Delete image from GridFS
        if (menu.getImageId() != null) {
            deleteImage(menu.getImageId());
        }

        // Delete menu item
        menuRepository.delete(menu);
    }

    /**
     * Map Menu entity to MenuResponse DTO
     */
    private MenuResponse mapToResponse(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .category(menu.getCategory())
                .imageId(menu.getImageId())
                .imageUrl(menu.getImageId() != null ? "/api/images/" + menu.getImageId() : null)
                .createdAt(menu.getCreatedAt())
                .updatedAt(menu.getUpdatedAt())
                .available(menu.getAvailable())
                .build();
    }

}

