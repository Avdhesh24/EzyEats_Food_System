package com.Food_Delivery_System.EzyEats.repositories;

import com.Food_Delivery_System.EzyEats.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {

}
