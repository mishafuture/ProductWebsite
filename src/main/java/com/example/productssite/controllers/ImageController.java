package com.example.productssite.controllers;

import com.example.productssite.entities.Image;
import com.example.productssite.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable("id") Long id) {
        Image image = imageService.getImages(id);

        if (image != null) {
            ByteArrayResource resource = new ByteArrayResource(image.getImageData());

            return ResponseEntity.ok()
                    .header("fileName", image.getName())
                    .contentType(MediaType.valueOf(image.getType()))
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }
}
