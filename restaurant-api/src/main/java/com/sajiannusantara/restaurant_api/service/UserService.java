package com.sajiannusantara.restaurant_api.service;

import com.sajiannusantara.restaurant_api.model.PasswordResetToken;
import com.sajiannusantara.restaurant_api.model.User;
import com.sajiannusantara.restaurant_api.repository.PasswordResetTokenRepository;
import com.sajiannusantara.restaurant_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Auto-assign role based on email pattern
        if (user.getEmail() != null && user.getEmail().endsWith("@admin.com")) {
            user.setRole("ADMIN");
        } else {
            user.setRole("USER");
        }
        
        // Set creation timestamp
        user.setCreatedAt(LocalDateTime.now());
        
        return userRepository.save(user);
    }

    public User login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    public String generatePasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email"));

        // Delete any existing tokens for this email
        passwordResetTokenRepository.deleteByEmail(email);

        // Generate new token
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setEmail(email);
        resetToken.setExpiryDate(new Date(System.currentTimeMillis() + 3600000)); // 1 hour
        resetToken.setUsed(false);

        passwordResetTokenRepository.save(resetToken);

        // Send password reset email
        emailService.sendPasswordResetEmail(email, token);

        // Log for development/testing (remove in production or use proper logging)
        System.out.println("Password reset email sent to: " + email);
        System.out.println("Reset link: " + "http://localhost:5173/reset-password?token=" + token);

        return token;
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired reset token"));

        if (resetToken.isUsed()) {
            throw new RuntimeException("This reset token has already been used");
        }

        if (resetToken.getExpiryDate().before(new Date())) {
            throw new RuntimeException("Reset token has expired");
        }

        User user = userRepository.findByEmail(resetToken.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Mark token as used
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);
    }

    public User updateUserRole(User user) {
        return userRepository.save(user);
    }

    public User findOrCreateGoogleUser(String email, String username, String fullName, String googleId) {
        // Check if user exists by email
        Optional<User> existingUser = userRepository.findByEmail(email);
        
        if (existingUser.isPresent()) {
            // User exists, return it
            return existingUser.get();
        }
        
        // User doesn't exist, create new user
        User newUser = new User();
        newUser.setEmail(email);
        
        // Generate unique username if provided username already exists
        String finalUsername = username;
        int suffix = 1;
        while (userRepository.findByUsername(finalUsername).isPresent()) {
            finalUsername = username + "_" + suffix;
            suffix++;
        }
        newUser.setUsername(finalUsername);
        
        // Set a random password (Google users don't need password, but we need to store something)
        // In production, you might want to handle this differently
        newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        
        // Auto-assign role based on email pattern
        if (email != null && email.endsWith("@admin.com")) {
            newUser.setRole("ADMIN");
        } else {
            newUser.setRole("USER");
        }
        
        // Set creation timestamp
        newUser.setCreatedAt(LocalDateTime.now());
        
        return userRepository.save(newUser);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

}

