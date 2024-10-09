package com.Food_Delivery_System.EzyEats.service;

import com.Food_Delivery_System.EzyEats.models.Payment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface PaymentService {

    List<Payment> getAllPayments();

    Payment getPaymentId(int paymentId);

    Payment createPayment(Payment payment);

    void deletePayment(int paymentId);
}
