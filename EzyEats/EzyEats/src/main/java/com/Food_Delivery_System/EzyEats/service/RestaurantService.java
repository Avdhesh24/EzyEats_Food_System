package com.Food_Delivery_System.EzyEats.service;

import com.Food_Delivery_System.EzyEats.models.Restaurant;
import java.util.List;

public interface RestaurantService {

    Restaurant registerRestaurant(Restaurant restaurant);

    List<Restaurant> getAllRestaurants();

    Restaurant getRestaurantById(int restaurantId);

    Restaurant updateRestaurant(int restaurantId, Restaurant updatedRestaurant);

    boolean deleteRestaurant(int restaurantId);
}
