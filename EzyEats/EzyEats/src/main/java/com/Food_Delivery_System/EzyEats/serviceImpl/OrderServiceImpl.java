package com.Food_Delivery_System.EzyEats.serviceImpl;

import com.Food_Delivery_System.EzyEats.models.Order;
import com.Food_Delivery_System.EzyEats.repositories.OrderRepo;
import com.Food_Delivery_System.EzyEats.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public Order placeOrder(Order order) {
        return orderRepo.save(order);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepo.findAll();
    }

    @Override
    public Order getOrderById(int orderId) {
        Optional<Order> order = orderRepo.findById(orderId);
        return order.orElse(null);
    }

    @Override
    public Order updateOrderStatus(int orderId, String status) {
        Optional<Order> orderOptional = orderRepo.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setOrderStatus(status);
            return orderRepo.save(order);
        }
        return null;
    }

    @Override
    public boolean cancelOrder(int orderId) {
        Optional<Order> orderOptional = orderRepo.findById(orderId);
        if (orderOptional.isPresent() && !orderOptional.get().getOrderStatus().equals("Prepared")) {
            orderRepo.deleteById(orderId);
            return true;
        }
        return false;
    }
}
