package com.Food_Delivery_System.EzyEats.serviceImpl;

import com.Food_Delivery_System.EzyEats.models.Delivery;
import com.Food_Delivery_System.EzyEats.models.Order;
import com.Food_Delivery_System.EzyEats.repositories.DeliveryRepo;
import com.Food_Delivery_System.EzyEats.repositories.OrderRepo;
import com.Food_Delivery_System.EzyEats.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepo deliveryRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public Delivery assignDelivery(int orderId, int deliveryExecutiveId) {
        Optional<Order> orderOptional = orderRepo.findById(orderId);
        if (orderOptional.isPresent()) {
            Delivery delivery = new Delivery();
            delivery.setOrder(orderOptional.get());
            delivery.setDeliveryExecutiveId(deliveryExecutiveId);
            delivery.setEstimatedDeliveryTime(new Date());
            delivery.setDeliveryStatus("In Transit");

            return deliveryRepo.save(delivery);
        }
        return null; // Order not found
    }

    @Override
    public List<Delivery> getDeliveriesByExecutive(int deliveryExecutiveId) {
        return deliveryRepo.findByDeliveryExecutiveId(deliveryExecutiveId);
    }

    @Override
    public Delivery updateDeliveryStatus(int orderId, String status) {
        Optional<Delivery> deliveryOptional = deliveryRepo.findByOrder_Id(orderId); // Updated to findByOrder_Id
        if (deliveryOptional.isPresent()) {
            Delivery delivery = deliveryOptional.get();
            delivery.setDeliveryStatus(status);
            return deliveryRepo.save(delivery);
        }
        return null; // Delivery not found
    }

    @Override
    public Delivery getDeliveryDetails(int deliveryId) {
        return deliveryRepo.findById(deliveryId).orElse(null); // Fetch delivery details by ID
    }
}
