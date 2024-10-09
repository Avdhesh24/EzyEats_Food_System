package com.Food_Delivery_System.EzyEats.service;

import com.Food_Delivery_System.EzyEats.models.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Order order);// Place a new order

    List<Order> getAllOrder(); // Get All Order

    Order getOrderById(int orderId); // Get A Specific Order

    Order updateOrderStatus(int orderId, String status);  // Update the status of an order

    boolean cancelOrder(int orderId); // Cancel an order


}
