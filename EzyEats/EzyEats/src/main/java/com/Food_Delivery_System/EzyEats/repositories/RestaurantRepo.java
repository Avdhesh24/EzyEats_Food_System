package com.Food_Delivery_System.EzyEats.repositories;

import com.Food_Delivery_System.EzyEats.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepo extends JpaRepository<Restaurant, Integer> {
}
