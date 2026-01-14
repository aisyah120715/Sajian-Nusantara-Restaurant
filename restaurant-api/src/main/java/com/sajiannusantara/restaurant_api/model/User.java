package com.sajiannusantara.restaurant_api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String phoneNumber;

    private String role; // "USER" or "ADMIN"

    /**
     * Timestamp when the user was created
     */
    private LocalDateTime createdAt;

}

