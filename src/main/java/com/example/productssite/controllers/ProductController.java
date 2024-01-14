package com.example.productssite.controllers;

import com.example.productssite.entities.Product;
import com.example.productssite.services.ProductService;
import com.example.productssite.services.UserProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserProductService userProductService;

    @ModelAttribute("principal")
    public Principal getPrincipalAttribute(Principal principal) {
        return principal;
    }

    @GetMapping("/products")
    public String showAllProducts(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("anonymousUser".equals(authentication.getPrincipal()));

        List<Product> products = productService.getAllProducts();

        if (products != null)
            model.addAttribute("products", products);

        return "index";
    }

    @GetMapping("/")
    public String redirectToAllProducts() {
        return "redirect:/products";
    }

    @GetMapping("/product/new")
    public String creationProduct(@ModelAttribute("product") Product product) {
        return "creation_product";
    }

    @PostMapping("/product/new")
    public String createProduct(@RequestParam("photo") MultipartFile image, @RequestParam("photo1") MultipartFile image1,
                                @RequestParam("photo2")MultipartFile image2, @RequestParam("photo3")MultipartFile image3,
                                @RequestParam("photo4")MultipartFile image4, @RequestParam("photo5")MultipartFile image5,
                                @Valid Product product, BindingResult bindingResult,
                                Principal principal) throws IOException {
        if(bindingResult.hasErrors())
            return "creation_product";

        Product productFromDb = productService.addProduct(product, image,
                image1, image2, image3, image4, image5);
        userProductService.saveUserAndProduct(productFromDb, principal);

        return "redirect:/products";
    }

    @GetMapping("/product/{id}")
    public String showDetails(@PathVariable("id") Long id, Model model,
                              Principal principal) {
        Product product = productService.getProductById(id);

        if (product == null)
            return "not_found_product";

        model.addAttribute("product", product);
        if (principal != null)
            model.addAttribute("isOwner",
                    userProductService.checkOwnership(principal, id));
        model.addAttribute("owner", userProductService.getUser(product));

        return "product_info";
    }

    @PreAuthorize("@userProductServiceImp.checkOwnership(#principal, #id)")
    @GetMapping("/product/{id}/edit")
    public String editionProduct(@PathVariable("id") Long id, Model model,
                                 Principal principal) {
        Product product = productService.getProductById(id);

        if (product == null)
            return "not_found_product";

        model.addAttribute("product", product);

        return "edition_product";
    }

    @PreAuthorize("@userProductServiceImp.checkOwnership(#principal, #id)")
    @PostMapping("/product/{id}/edit")
    public String editProduct(@Valid Product product, BindingResult bindingResult,
                              @PathVariable("id") Long id, Principal principal) {
        if (bindingResult.hasErrors())
            return "edition_product";

        productService.editProduct(product);

        Product productFromDB = productService.getProductById(product.getId());

        userProductService.editProduct(productFromDB);

        return "redirect:/products";
    }

    @PreAuthorize("@userProductServiceImp.checkOwnership(#principal, #id)")
    @PostMapping("product/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id, Principal principal) {
        Product product = productService.getProductById(id);

        userProductService.deleteProduct(product);
        productService.deleteProductById(id);

        return "redirect:/products";
    }

    @GetMapping("product/{id}/images")
    public ResponseEntity<List<Long>> getProductImages(@PathVariable Long id) {
        List<Long> images = productService.getAllImagesId(id);

        return ResponseEntity.ok(images);
    }
}
