package com.Food_Delivery_System.EzyEats.controllers;

import com.Food_Delivery_System.EzyEats.models.Restaurant;
import com.Food_Delivery_System.EzyEats.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // Register a new restaurant (Restaurant owner only)
    @PostMapping
    public ResponseEntity<Restaurant> registerRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant createdRestaurant = restaurantService.registerRestaurant(restaurant);
        return ResponseEntity.status(201).body(createdRestaurant);  // 201 Created
    }

    // Get a list of all restaurants
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);  // 200 OK
    }

    // Get restaurant details by ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable int id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return restaurant != null ? ResponseEntity.ok(restaurant) : ResponseEntity.notFound().build();  // 200 OK or 404 Not Found
    }

    // Update restaurant information
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable int id, @RequestBody Restaurant updatedRestaurant) {
        Restaurant restaurant = restaurantService.updateRestaurant(id, updatedRestaurant);
        return restaurant != null ? ResponseEntity.ok(restaurant) : ResponseEntity.notFound().build();  // 200 OK or 404 Not Found
    }

    // Remove a restaurant from the platform (Admin only)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable int id) {
        boolean deleted = restaurantService.deleteRestaurant(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();  // 204 No Content or 404 Not Found
    }
}
