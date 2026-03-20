package com.ecom.catalog.repository;

import com.ecom.catalog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String name);
    List<Category> findByActiveTrue();
    Optional<Category> findByName(String name);
}
