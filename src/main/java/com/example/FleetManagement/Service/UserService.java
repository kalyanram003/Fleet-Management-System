package com.example.FleetManagement.Service;


import com.example.FleetManagement.Entity.User;

import java.util.List;
import java.util.Optional;


public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByPhone(String phone);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}
