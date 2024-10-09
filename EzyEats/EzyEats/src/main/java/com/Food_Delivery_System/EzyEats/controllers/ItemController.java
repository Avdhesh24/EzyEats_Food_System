package com.Food_Delivery_System.EzyEats.controllers;

import com.Food_Delivery_System.EzyEats.models.Item;
import com.Food_Delivery_System.EzyEats.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/menu")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // POST /restaurants/{restaurantId}/menu: Add a new menu item to a restaurant
    @PostMapping
    public ResponseEntity<Item> addItemToMenu(@PathVariable int restaurantId, @RequestBody Item item) {
        Item createdItem = itemService.addItemToMenu(restaurantId, item);
        if (createdItem != null) {
            return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Restaurant not found
    }

    // GET /restaurants/{restaurantId}/menu: Get all menu items of a restaurant
    @GetMapping
    public ResponseEntity<List<Item>> getMenuItemsByRestaurant(@PathVariable int restaurantId) {
        List<Item> menuItems = itemService.getMenuItemsByRestaurant(restaurantId);
        if (menuItems != null) {
            return new ResponseEntity<>(menuItems, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Restaurant not found
    }

    // PUT /restaurants/{restaurantId}/menu/{itemId}: Update details of a menu item
    @PutMapping("/{itemId}")
    public ResponseEntity<Item> updateMenuItem(@PathVariable int restaurantId, @PathVariable int itemId, @RequestBody Item updatedItem) {
        Item updatedMenuItem = itemService.updateMenuItem(restaurantId, itemId, updatedItem);
        if (updatedMenuItem != null) {
            return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Restaurant or item not found
    }

    // DELETE /restaurants/{restaurantId}/menu/{itemId}: Delete a menu item from the restaurant
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable int restaurantId, @PathVariable int itemId) {
        if (itemService.deleteMenuItem(restaurantId, itemId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Successfully deleted
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Restaurant or item not found
    }
}
