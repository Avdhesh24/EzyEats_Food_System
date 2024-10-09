package com.Food_Delivery_System.EzyEats.serviceImpl;

import com.Food_Delivery_System.EzyEats.models.User;
import com.Food_Delivery_System.EzyEats.repositories.UserRepo;
import com.Food_Delivery_System.EzyEats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepo.save(user);
    }

    @Override
    public User loginUser(String username, String password) {
        Optional<User> optionalUser = userRepo.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Compare raw password with the encoded password stored in DB
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;  // Return null for invalid credentials
    }

    @Override
    public User getUserById(int userId) {
        return userRepo.findById(userId).orElse(null);  // Return null if not found
    }

    @Override
    public User updateUser(int userId, User updatedUser) {
        if (userRepo.existsById(userId)) {
            if (updatedUser.getPassword() != null) {
                String hashedPassword = passwordEncoder.encode(updatedUser.getPassword());
                updatedUser.setPassword(hashedPassword);
            }
            updatedUser.setUserId(userId);
            return userRepo.save(updatedUser);  // Save updated user
        }
        return null;
    }

    @Override
    public User findByUserName(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    @Override
    public boolean deleteUser(int userId) {
        if (userRepo.existsById(userId)) {
            userRepo.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
