package com.example.productssite.services;

import com.example.productssite.entities.User;

import java.util.List;

public interface UserService {

    void save(User user);
    boolean isEmailUnique(String email);
    User getUserById(Long id);
    User getUserByEmail(String email);

    List<User> getAllUsers();

    void editWithoutPassword(User user);

    void changeActivity(Long id);

    void changeRole(Long id);
}
