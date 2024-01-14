package com.example.productssite.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "Please, enter product's title")
    @Size(min = 1, max = 25, message = "Title must be between 1 and 30 characters")
    @Column(name = "title")
    private String title;
    @Column(name = "price")
    private double price;
    @Size(max = 200, message = "The maximum description length is 200 characters.")
    @Column(name = "description")
    private String description;
    @Column(name = "date")
    private LocalDateTime date;
    @OneToOne(mappedBy = "product")
    private UserProduct userProducts;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> images = new ArrayList<>(3);

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", Locale.getDefault());
        return date.format(formatter);
    }

    public void addImageToProduct(Image image) {
        image.setProduct(this);
        images.add(image);
    }
}
