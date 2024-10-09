package com.Food_Delivery_System.EzyEats.security;

import com.Food_Delivery_System.EzyEats.security.jwt.JwtGeneratorFilter;
import com.Food_Delivery_System.EzyEats.security.jwt.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtValidationFilter jwtValidationFilter;

    @Autowired
    private JwtGeneratorFilter jwtGeneratorFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoder for user authentication
    }

    // AuthenticationManager bean for authentication process
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }


    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // Allow all origins (for development purposes)
        corsConfiguration.addAllowedMethod(HttpMethod.GET);
        corsConfiguration.addAllowedMethod(HttpMethod.POST);
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        corsConfiguration.addAllowedHeader("*"); // Allow all headers
        corsConfiguration.setAllowCredentials(true); // Allow credentials (cookies, authorization headers)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // Apply to all endpoints

        return new CorsFilter(source);
    }

    // Security filter chain for configuring security settings
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session management
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/users/register", "/users/login").permitAll() // Allow login and registration without authentication
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui*/**").permitAll() // Swagger docs
                        .requestMatchers("/restaurants/**").hasRole("OWNER") // Only OWNER can access restaurant-related endpoints
                        .requestMatchers("/orders/**").hasRole("USER") // Only USER can access orders-related endpoints
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Only ADMIN can access admin-related endpoints
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .addFilter(UsernamePasswordAuthenticationFilter.class.newInstance())
                .addFilterAfter(new JwtGeneratorFilter(),BasicAuthenticationFilter.class) // JWT generation filter after UsernamePasswordAuthenticationFilter
                .addFilterBefore(new JwtValidationFilter(), BasicAuthenticationFilter.class) // JWT validation filter before UsernamePasswordAuthenticationFilter
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for stateless authentication

        ;

        return http.build();
    }
}
