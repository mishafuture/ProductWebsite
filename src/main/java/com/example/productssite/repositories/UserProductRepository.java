package com.example.productssite.repositories;

import com.example.productssite.entities.Product;
import com.example.productssite.entities.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProductRepository extends JpaRepository<UserProduct, Long> {
    UserProduct findUserProductsByProductId(Long id);

    UserProduct findUserProductsByUserrId(Long id);
}
