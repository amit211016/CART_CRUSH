package com.ecom.catalog.config;

import com.ecom.catalog.model.Category;
import com.ecom.catalog.model.Product;
import com.ecom.catalog.repository.CategoryRepository;
import com.ecom.catalog.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedCatalog(CategoryRepository categoryRepository,
                                  ProductRepository productRepository) {
        return args -> seedIfEmpty(categoryRepository, productRepository);
    }

    @Transactional
    void seedIfEmpty(CategoryRepository categoryRepository,
                     ProductRepository productRepository) {
        if (categoryRepository.count() == 0) {
            Category electronics = createCategory("Electronics", "mobile.png");
            Category laptops = createCategory("Laptops", "laptop.jpg");
            Category fashion = createCategory("Fashion", "pant.png");

            categoryRepository.saveAll(List.of(electronics, laptops, fashion));
        }

        if (productRepository.count() == 0) {
            Category electronics = categoryRepository.findByName("Electronics").orElseThrow();
            Category laptops = categoryRepository.findByName("Laptops").orElseThrow();
            Category fashion = categoryRepository.findByName("Fashion").orElseThrow();

            Product phone = createProduct("Pixel 8", "Latest Google flagship", 79999D, 10,
                    "laptop.jpg", 10, electronics);
            Product laptop = createProduct("ThinkPad X1", "Carbon lightweight business laptop", 159999D, 5,
                    "laptop.jpg", 15, laptops);
            Product tshirt = createProduct("Graphic Tee", "Cotton crew neck", 1999D, 50,
                    "laptop.jpg", 25, fashion); // reuse existing image

            productRepository.saveAll(List.of(phone, laptop, tshirt));
        }

        // Normalize existing product data (discount price and image defaults)
        var normalized = productRepository.findAll();
        normalized.forEach(p -> {
            if (p.getDiscountPrice() == null && p.getDiscount() != null && p.getPrice() != null) {
                p.setDiscountPrice(p.getPrice() - (p.getPrice() * p.getDiscount() / 100.0));
            }
            if (p.getImageName() == null || p.getImageName().isBlank() || !p.getImageName().equals("laptop.jpg")) {
                p.setImageName("laptop.jpg");
            }
        });
        productRepository.saveAll(normalized);
    }

    private Category createCategory(String name, String imageName) {
        Category c = new Category();
        c.setName(name);
        c.setImageName(imageName);
        c.setActive(true);
        return c;
    }

    private Product createProduct(String title, String description, Double price, Integer stock,
                                  String imageName, Integer discount, Category category) {
        Product p = new Product();
        p.setTitle(title);
        p.setDescription(description);
        p.setPrice(price);
        p.setDiscount(discount);
        p.setDiscountPrice(price - (price * discount / 100.0));
        p.setStock(stock);
        p.setImageName(imageName);
        p.setActive(true);
        p.setCategory(category);
        return p;
    }
}
