package com.Food_Delivery_System.EzyEats.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int itemId;


    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;



    private String name;
    private int quantity;
    private double price;
}

