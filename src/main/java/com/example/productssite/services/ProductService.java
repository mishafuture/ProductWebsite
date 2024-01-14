package com.example.productssite.services;

import com.example.productssite.entities.Image;
import com.example.productssite.entities.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {
    List<Product> getAllProducts();
    Product addProduct(Product newProduct, MultipartFile... files) throws IOException;
    Product getProductById(Long id);
    Product editProduct(Product newProduct);
    boolean deleteProductById(Long id);

    List<Long> getAllImagesId(Long id);
}
