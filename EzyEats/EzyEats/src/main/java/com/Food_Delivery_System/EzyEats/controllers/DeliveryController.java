package com.Food_Delivery_System.EzyEats.controllers;

import com.Food_Delivery_System.EzyEats.models.Delivery;
import com.Food_Delivery_System.EzyEats.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("/assign")
    public ResponseEntity<Delivery> assignDelivery(@RequestParam int orderId, @RequestParam int deliveryExecutiveId) {
        Delivery delivery = deliveryService.assignDelivery(orderId, deliveryExecutiveId);
        if (delivery != null) {
            return ResponseEntity.ok(delivery); // Return 200 OK
        }
        return ResponseEntity.notFound().build(); // Return 404 if order not found
    }

    @GetMapping
    public ResponseEntity<List<Delivery>> getDeliveriesByExecutive(@RequestParam int deliveryExecutiveId) {
        List<Delivery> deliveries = deliveryService.getDeliveriesByExecutive(deliveryExecutiveId);
        if (deliveries.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no deliveries found
        }
        return ResponseEntity.ok(deliveries); // Return 200 OK with the list of deliveries
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Delivery> updateDeliveryStatus(@PathVariable int orderId, @RequestBody String status) {
        Delivery updatedDelivery = deliveryService.updateDeliveryStatus(orderId, status);
        if (updatedDelivery != null) {
            return ResponseEntity.ok(updatedDelivery); // Return 200 OK with updated delivery details
        }
        return ResponseEntity.notFound().build(); // Return 404 if delivery not found
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryDetails(@PathVariable int id) {
        Delivery delivery = deliveryService.getDeliveryDetails(id);
        if (delivery != null) {
            return ResponseEntity.ok(delivery); // Return 200 OK with delivery details
        }
        return ResponseEntity.notFound().build(); // Return 404 if delivery not found
    }
}
