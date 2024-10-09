package com.Food_Delivery_System.EzyEats.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtGeneratorFilter extends OncePerRequestFilter {

    private static final long JWT_EXPIRATION_TIME = 30000000L;  // 30 minutes expiration time

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("Into Generator");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Only generate JWT for authenticated requests
        if (authentication != null && authentication.isAuthenticated()) {
            // Use your secret key configured in SecurityConstants
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());

            String jwt = Jwts.builder()
                    .setIssuer("EzyEats")
                    .setSubject(authentication.getName())  // Typically the username or user identifier
                    .claim("roles", getRoles(authentication.getAuthorities()))  // Assigning roles to the JWT
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + JWT_EXPIRATION_TIME))  // Set expiration
                    .signWith(key)
                    .compact();

            // Add JWT to the response header
            response.setHeader(SecurityConstants.JWT_HEADER, "Bearer " + jwt);
            log.info("JWT generated and added to response header for user: {}", authentication.getName());
        } else {
            log.warn("Authentication is null or unauthenticated request, skipping JWT generation.");
        }

        // Continue with the request chain
        filterChain.doFilter(request, response);
    }

    // Helper method to extract roles from the authorities
    private String getRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Skip this filter for login requests (no JWT generation during login)
        return request.getServletPath().equals("/users/login");
    }
}