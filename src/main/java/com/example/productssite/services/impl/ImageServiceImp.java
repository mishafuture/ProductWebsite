package com.example.productssite.services.impl;

import com.example.productssite.entities.Image;
import com.example.productssite.repositories.ImageRepository;
import com.example.productssite.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImp implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public Image getImages(Long id) {
        return imageRepository.getImageById(id).orElse(null);
    }
}
