package com.Food_Delivery_System.EzyEats.service;

import com.Food_Delivery_System.EzyEats.models.Item;
import java.util.List;

public interface ItemService {

    Item addItemToMenu(int restaurantId, Item item);  // Add new item to restaurant menu

    List<Item> getMenuItemsByRestaurant(int restaurantId);  // Get all menu items of a restaurant

    Item updateMenuItem(int restaurantId, int itemId, Item updatedItem);  // Update menu item

    boolean deleteMenuItem(int restaurantId, int itemId);  // Delete a menu item from restaurant
}
