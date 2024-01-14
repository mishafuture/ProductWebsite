package com.example.productssite.repositories;

import com.example.productssite.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> getImageById(Long id);
}
