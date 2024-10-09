package com.Food_Delivery_System.EzyEats.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Delivery")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deliveryId;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false) // Ensure order cannot be null
    private Order order;

    @Column(name = "deliveryExecutiveId", nullable = false) // Ensure delivery executive ID cannot be null
    private int deliveryExecutiveId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "estimatedDeliveryTime")
    private Date estimatedDeliveryTime;

    @Column(name = "deliveryStatus", nullable = false) // Ensure status cannot be null
    private String deliveryStatus;
}
