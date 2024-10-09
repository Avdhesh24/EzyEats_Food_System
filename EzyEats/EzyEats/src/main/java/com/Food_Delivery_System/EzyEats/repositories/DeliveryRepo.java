package com.Food_Delivery_System.EzyEats.repositories;

import com.Food_Delivery_System.EzyEats.models.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepo extends JpaRepository<Delivery, Integer> {
    List<Delivery> findByDeliveryExecutiveId(int deliveryExecutiveId);
    Optional<Delivery> findByOrder_Id(int orderId); // Change orderId to id
}
