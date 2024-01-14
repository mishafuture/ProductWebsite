package com.example.productssite.services.impl;

import com.example.productssite.entities.Image;
import com.example.productssite.entities.Product;
import com.example.productssite.repositories.ImageRepository;
import com.example.productssite.repositories.ProductRepository;
import com.example.productssite.services.ProductService;
import com.example.productssite.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(Product newProduct, MultipartFile... files) throws IOException {
        newProduct.setDate(LocalDateTime.now());

        for (MultipartFile file : files) {
            if (file.getSize() != 0) {
                Image image = toImageEntity(file);
                newProduct.addImageToProduct(image);
            }
        }

        return productRepository.save(newProduct);
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product editProduct(Product newProduct) {
        newProduct.setDate(LocalDateTime.now());

        return productRepository.save(newProduct);
    }

    @Override
    public boolean deleteProductById(Long id) {
        if (!productRepository.existsById(id))
            return false;

        productRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Long> getAllImagesId(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        List<Image> images;
        
        if (product == null)
            return null;

        images = product.getImages();
        List<Long> imagesId = new ArrayList<>();

        for (Image image : images) {
            imagesId.add(image.getId());
        }

        return imagesId;
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        return Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(file.getBytes()).build();
    }
}
