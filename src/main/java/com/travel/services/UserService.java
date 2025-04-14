package com.travel.services;

import java.util.List;
import com.travel.entities.User;

public interface UserService {

    User registerUser(User user);

    User loginUser(String email, String password);

    List<User> getAllUsers();

    User updateUser(Long id, User userUpdates);

    void deleteUser(Long id);
}