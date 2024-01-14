package com.example.productssite.services;

import com.example.productssite.entities.Product;
import com.example.productssite.entities.User;

import java.security.Principal;

public interface UserProductService {
    void saveUserAndProduct(Product product, Principal principal);
    void editProduct(Product product);
    void deleteProduct(Product product);
    boolean checkOwnership(Principal principal, Long productId);

    User getUser(Product product);
}
