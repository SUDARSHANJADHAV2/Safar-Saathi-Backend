package com.travel.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.travel.entities.User;
import com.travel.repositories.UserRepository;
import com.travel.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder; // ✅ added

    @Override
    public User registerUser(User user) {
        if (user.getEmail() != null)
            user.setEmail(user.getEmail().trim());

        if (user.getPassword() != null)
            user.setPassword(passwordEncoder.encode(user.getPassword().trim())); // ✅ encrypted

        if (userRepo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        return userRepo.save(user);
    }

    @Override
    public User loginUser(String email, String password) {
        String cleanEmail = (email != null) ? email.trim() : "";
        String cleanPassword = (password != null) ? password.trim() : "";

        User user = userRepo.findByEmail(cleanEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + cleanEmail));

        // ✅ FIXED login check
        if (!passwordEncoder.matches(cleanPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User updateUser(Long id, User userUpdates) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        if (userUpdates.getName() != null)
            existingUser.setName(userUpdates.getName());

        if (userUpdates.getEmail() != null)
            existingUser.setEmail(userUpdates.getEmail().trim());

        if (userUpdates.getUserRole() != null)
            existingUser.setUserRole(userUpdates.getUserRole());

        // ✅ IMPORTANT: encrypt password here also
        if (userUpdates.getPassword() != null)
            existingUser.setPassword(passwordEncoder.encode(userUpdates.getPassword().trim()));

        return userRepo.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }
}