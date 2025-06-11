package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.ecom.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	//to check whether the category exist or not
	public Boolean existsByName(String name);
	
	public abstract List<Category> findByIsActiveTrue();
	
}
