package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	public abstract List<Product> findByCategoryAndIsActiveTrue(String category);
	
	public abstract List<Product> findByIsActiveTrue();
	
	
}
