package com.Food_Delivery_System.EzyEats.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        log.info("Inside JWT validation filter.");

        String jwt = request.getHeader(SecurityConstants.JWT_HEADER);

        if (jwt != null) {
            try {
                // Extract the "Bearer " prefix
                if (!jwt.startsWith("Bearer ")) {
                    throw new BadCredentialsException("Invalid JWT Token format.");
                }
                jwt = jwt.substring(7);

                SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());

                // Parse the JWT and get claims
                Claims claims = Jwts.parser()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                // Check token expiration
                Date expiration = claims.getExpiration();
                if (expiration != null && expiration.before(new Date())) {
                    throw new BadCredentialsException("JWT Token has expired.");
                }

                // Extract username (subject) and roles from claims
                String username = claims.getSubject();  // The subject is typically the username
                String roles = (String) claims.get("roles");

                if (username == null || roles == null) {
                    throw new BadCredentialsException("Missing username or roles in JWT.");
                }

                // Split roles if multiple roles are assigned
                List<GrantedAuthority> authorities = new ArrayList<>();
                for (String role : roles.split(",")) {
                    authorities.add(new SimpleGrantedAuthority(role.trim())); // Ensure there's no extra whitespace
                }

                // Create Authentication object and set it in SecurityContextHolder
                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (BadCredentialsException e) {
                log.error("Invalid JWT token: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized: Invalid or expired JWT token.");
                return; // Return immediately in case of error
            } catch (Exception e) {
                log.error("JWT processing failed: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized: Invalid JWT Token.");
                return; // Return immediately in case of error
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/users/login"); // Exclude login path from filtering
    }
}
