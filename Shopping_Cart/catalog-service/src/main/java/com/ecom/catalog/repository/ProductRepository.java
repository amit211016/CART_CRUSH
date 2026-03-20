package com.ecom.catalog.repository;

import com.ecom.catalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory_NameAndActiveTrue(String categoryName);
    List<Product> findByActiveTrue();
}
