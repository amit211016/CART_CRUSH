package com.ecom.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Category;


public interface CategoryService {
	
	
	public Boolean existsByName(String name);
	
	public Category saveCategory(Category category);
	
	public List<Category> getAllCategory();

	public boolean deleteCategory(int id);
	
	public Category getCategoryById(int id);
	
	public abstract Category updateCategory(Category category, MultipartFile image);
	
	public abstract Category saveCategory(Category category, MultipartFile file) throws IOException;
	
	public abstract List<Category> findByIsActiveTrue();

}
