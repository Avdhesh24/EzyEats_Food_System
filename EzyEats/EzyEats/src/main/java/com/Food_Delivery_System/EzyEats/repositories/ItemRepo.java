package com.Food_Delivery_System.EzyEats.repositories;

import com.Food_Delivery_System.EzyEats.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Integer> {
    List<Item> findByRestaurant_Id(int id);
}
