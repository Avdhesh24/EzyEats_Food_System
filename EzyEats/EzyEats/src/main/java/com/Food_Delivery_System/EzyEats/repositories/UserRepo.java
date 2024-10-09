package com.Food_Delivery_System.EzyEats.repositories;

import com.Food_Delivery_System.EzyEats.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
   Optional<User> findByUsername(String username);
}
