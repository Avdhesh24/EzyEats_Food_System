package com.Food_Delivery_System.EzyEats.serviceImpl;

import com.Food_Delivery_System.EzyEats.models.Restaurant;
import com.Food_Delivery_System.EzyEats.repositories.RestaurantRepo;
import com.Food_Delivery_System.EzyEats.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Override
    public Restaurant registerRestaurant(Restaurant restaurant) {
        return restaurantRepo.save(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

    @Override
    public Restaurant getRestaurantById(int restaurantId) {
        return restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant with ID " + restaurantId + " not found"));
    }

    @Override
    @Transactional
    public Restaurant updateRestaurant(int restaurantId, Restaurant updatedRestaurant) {
        if (restaurantRepo.existsById(restaurantId)) {
            updatedRestaurant.setId(restaurantId);  // Use setId instead of setRestaurantId
            return restaurantRepo.save(updatedRestaurant);
        } else {
            throw new IllegalArgumentException("Restaurant with ID " + restaurantId + " not found");
        }
    }


    @Override
    @Transactional
    public boolean deleteRestaurant(int restaurantId) {
        if (restaurantRepo.existsById(restaurantId)) {
            restaurantRepo.deleteById(restaurantId);
            return true;
        } else {
            throw new IllegalArgumentException("Restaurant with ID " + restaurantId + " not found");
        }
    }
}
