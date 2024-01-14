package com.example.productssite.services;

import com.example.productssite.entities.User;

public interface AuthService {

    void doAutoLogin(String username, String password);
}
