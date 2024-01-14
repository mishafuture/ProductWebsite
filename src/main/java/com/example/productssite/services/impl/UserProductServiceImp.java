package com.example.productssite.services.impl;

import com.example.productssite.entities.Product;
import com.example.productssite.entities.User;
import com.example.productssite.entities.UserProduct;
import com.example.productssite.repositories.UserProductRepository;
import com.example.productssite.repositories.UserRepository;
import com.example.productssite.services.UserProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserProductServiceImp implements UserProductService {
    private final UserProductRepository userProductRepository;
    private final UserRepository userRepository;

    @Override
    public void saveUserAndProduct(Product product, Principal principal) {
        UserProduct userProduct = new UserProduct();
        userProduct.setProduct(product);
        userProduct.setUserr(getUserByPrincipal(principal));

        userProductRepository.save(userProduct);
    }

    @Override
    public void editProduct(Product product) {
        UserProduct userProduct = userProductRepository
                .findUserProductsByProductId(product.getId());

        userProduct.setProduct(product);

        userProductRepository.save(userProduct);
    }

    @Override
    public void deleteProduct(Product product) {
        UserProduct userProduct = userProductRepository
                .findUserProductsByProductId(product.getId());

        userProductRepository.delete(userProduct);
    }

    private User getUserByPrincipal(Principal principal) {
        return userRepository.findUserByEmail(principal.getName());
    }

    @Override
    public boolean checkOwnership(Principal principal, Long productId) {

        User owner = userProductRepository.findUserProductsByProductId(productId).getUserr();

        return Objects.equals(principal.getName(), owner.getEmail());
    }

    @Override
    public User getUser(Product product) {
        UserProduct userProduct = userProductRepository.findUserProductsByProductId(product.getId());

        return userProduct.getUserr();
    }
}
