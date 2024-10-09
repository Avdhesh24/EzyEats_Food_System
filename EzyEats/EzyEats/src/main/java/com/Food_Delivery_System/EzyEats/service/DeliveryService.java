package com.Food_Delivery_System.EzyEats.service;

import com.Food_Delivery_System.EzyEats.models.Delivery;

import java.util.List;

public interface DeliveryService {
    Delivery assignDelivery(int orderId, int deliveryExecutiveId);
    List<Delivery> getDeliveriesByExecutive(int deliveryExecutiveId);
    Delivery updateDeliveryStatus(int orderId, String status);
    Delivery getDeliveryDetails(int deliveryId);
}
