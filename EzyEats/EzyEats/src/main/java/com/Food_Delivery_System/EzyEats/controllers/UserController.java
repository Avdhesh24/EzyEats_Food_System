package com.Food_Delivery_System.EzyEats.controllers;

import com.Food_Delivery_System.EzyEats.models.User;
import com.Food_Delivery_System.EzyEats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }


// Register user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user);
        return ResponseEntity.ok(createdUser); // Return the created user
    }

    // Login user
    // Login user
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        try {
            // AuthenticationManager will handle authentication with username and password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(), user.getPassword()
                    )
            );

            // Set authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Return success response
            return ResponseEntity.ok("Login successful!");
        } catch (Exception e) {
            // Authentication failed, return 401 Unauthorized
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    // Get user profile information by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user); // Return 200 OK with user details
        }
        return ResponseEntity.notFound().build(); // Return 404 if user not found
    }

    // Update user profile details
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        if (user != null) {
            return ResponseEntity.ok(user); // Return 200 OK with updated user details
        }
        return ResponseEntity.notFound().build(); // Return 404 if user not found
    }

    // Delete a user account
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build(); // Return 204 No Content on successful deletion
        }
        return ResponseEntity.notFound().build(); // Return 404 if user not found
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.ok(List.of()); // Return 200 OK with an empty list if no users are found
        }
        return ResponseEntity.ok(users); // Return 200 OK with the list of users
    }
}
