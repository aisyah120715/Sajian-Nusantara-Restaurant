package com.sajiannusantara.restaurant_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * JWT Authentication Filter
 * Extracts JWT token from Authorization header and sets authentication context
 * Includes role-based authorities for access control
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Skip JWT filter for public endpoints (login, signup, forgot-password, etc.) and image retrieval
        // But process JWT for protected endpoints like /api/auth/me and /api/auth/profile
        if (path.startsWith("/api/images/")) {
            return true;
        }
        if (path.startsWith("/api/auth/")) {
            // Process JWT filter for authenticated endpoints
            if (path.equals("/api/auth/me") || path.equals("/api/auth/profile")) {
                return false; // Don't skip - process JWT
            }
            // Skip for public auth endpoints (login, signup, etc.)
            return true;
        }
        return false; // Process JWT for all other endpoints
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                
                if (jwtUtil.validateToken(token)) {
                    String email = jwtUtil.extractEmail(token);
                    String role = jwtUtil.extractRole(token);
                    
                    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        // Create authorities based on role
                        List<SimpleGrantedAuthority> authorities = Collections.emptyList();
                        if (role != null) {
                            // Add ROLE_ prefix for Spring Security
                            authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
                        }
                        
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        email, null, authorities);
                        authentication.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (Exception e) {
            // Log error but don't block the request
            // If token is invalid, Spring Security will handle it
            logger.error("Error processing JWT token", e);
        }

        filterChain.doFilter(request, response);
    }

}

