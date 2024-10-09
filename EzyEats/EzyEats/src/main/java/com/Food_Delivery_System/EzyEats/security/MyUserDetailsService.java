package com.Food_Delivery_System.EzyEats.security;

import com.Food_Delivery_System.EzyEats.models.User;
import com.Food_Delivery_System.EzyEats.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {


        @Autowired
        private UserRepo userRepo;


        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // Fetch the user from the database using the repository
            User user = userRepo.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

            // Convert the comma-separated role string to an array
            String[] roles = user.getRole().split(",");

            // Return a UserDetails object for Spring Security to use
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())  // Ensure this is consistent with your login request
                    .password(user.getPassword())  // Password should be encrypted (BCrypt)
                    .roles(roles)  // Assuming roles are stored as comma-separated string
                    .build();
        }

    }

