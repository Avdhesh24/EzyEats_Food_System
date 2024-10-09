package com.Food_Delivery_System.EzyEats.serviceImpl;

import com.Food_Delivery_System.EzyEats.models.Item;
import com.Food_Delivery_System.EzyEats.models.Restaurant;
import com.Food_Delivery_System.EzyEats.repositories.ItemRepo;
import com.Food_Delivery_System.EzyEats.repositories.RestaurantRepo;
import com.Food_Delivery_System.EzyEats.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Override
    public Item addItemToMenu(int restaurantId, Item item) {
        Optional<Restaurant> restaurantOptional = restaurantRepo.findById(restaurantId);
        if (restaurantOptional.isPresent()) {
            item.setRestaurant(restaurantOptional.get());
            return itemRepo.save(item);
        }
        return null; // Restaurant not found
    }

    @Override
    public List<Item> getMenuItemsByRestaurant(int restaurantId) {
        return itemRepo.findByRestaurant_Id(restaurantId); // Use findByRestaurant_Id
    }


    @Override
    public Item updateMenuItem(int restaurantId, int itemId, Item updatedItem) {
        Optional<Restaurant> restaurantOptional = restaurantRepo.findById(restaurantId);
        if (restaurantOptional.isPresent()) {
            Optional<Item> itemOptional = itemRepo.findById(itemId);
            if (itemOptional.isPresent()) {
                Item existingItem = itemOptional.get();
                existingItem.setName(updatedItem.getName());
                existingItem.setQuantity(updatedItem.getQuantity());
                existingItem.setPrice(updatedItem.getPrice());
                return itemRepo.save(existingItem);
            }
        }
        return null; // Restaurant or item not found
    }

    @Override
    public boolean deleteMenuItem(int restaurantId, int itemId) {
        Optional<Restaurant> restaurantOptional = restaurantRepo.findById(restaurantId);
        if (restaurantOptional.isPresent()) {
            Optional<Item> itemOptional = itemRepo.findById(itemId);
            if (itemOptional.isPresent()) {
                itemRepo.delete(itemOptional.get());
                return true; // Item successfully deleted
            }
        }
        return false; // Restaurant or item not found
    }
}
