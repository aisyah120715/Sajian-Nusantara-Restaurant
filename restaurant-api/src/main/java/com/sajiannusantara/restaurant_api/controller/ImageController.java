package com.sajiannusantara.restaurant_api.controller;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Controller for retrieving images from GridFS
 * Public endpoint - no authentication required
 */
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final MongoDatabaseFactory mongoDatabaseFactory;

    /**
     * Get GridFS bucket for image retrieval
     */
    private GridFSBucket getGridFSBucket() {
        return GridFSBuckets.create(mongoDatabaseFactory.getMongoDatabase());
    }

    /**
     * Retrieve image by ID from GridFS
     * GET /api/images/{imageId}
     */
    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageId) {
        try {
            GridFSBucket gridFSBucket = getGridFSBucket();
            ObjectId fileId = new ObjectId(imageId);

            // Find the file in GridFS using Filters
            GridFSFile gridFSFile = gridFSBucket.find(Filters.eq("_id", fileId)).first();
            if (gridFSFile == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Read the file content
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            gridFSBucket.downloadToStream(fileId, outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            // Determine content type from filename or default to image/jpeg
            String contentType = "image/jpeg"; // Default
            String filename = gridFSFile.getFilename();
            if (filename != null) {
                String lowerFilename = filename.toLowerCase();
                if (lowerFilename.endsWith(".png")) {
                    contentType = "image/png";
                } else if (lowerFilename.endsWith(".gif")) {
                    contentType = "image/gif";
                } else if (lowerFilename.endsWith(".webp")) {
                    contentType = "image/webp";
                }
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentLength(imageBytes.length);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            // Invalid ObjectId format
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

