package com.ecom.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.repository.CategoryRepository;
import com.ecom.service.CategoryService;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Override
	public Category saveCategory(Category category) {
		
		return categoryRepository.save(category);
	}

	@Override
	public Boolean existsByName(String name) {
		
		return categoryRepository.existsByName(name);
	}
	
	@Override
	public List<Category> getAllCategory(){
		return categoryRepository.findAll();
	}

	@Override
	public boolean deleteCategory(int id) {
		Category category = categoryRepository.findById(id).orElse(null);
		if(!ObjectUtils.isEmpty(category)) {
			categoryRepository.delete(category);
			return true;
		}
		return false;
	}

	@Override
	public Category getCategoryById(int id) {
		Category category = categoryRepository.findById(id).orElse(null);
		return category;
	}

	@Override
	public Category updateCategory(Category category, MultipartFile image) {
		Category oldCategory = getCategoryById(category.getId());
		if(ObjectUtils.isEmpty(oldCategory)) {
			return null;
		}
		
		String imageName = image.isEmpty() ? oldCategory.getImageName() : image.getOriginalFilename();
		category.setImageName(imageName);
		Category updateCategory = saveCategory(category);
		
		if(!ObjectUtils.isEmpty(updateCategory)) {
			try {
				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
						+ image.getOriginalFilename());
				Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			return updateCategory;
		}
		return null;
	}

	@Override
	public Category saveCategory(Category category, MultipartFile image) throws IOException {
		String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
		category.setImageName(imageName);
		Category saveCategory = saveCategory(category);
		
		if(!ObjectUtils.isEmpty(saveCategory)) {
			File saveFile = new ClassPathResource("static/img").getFile();
			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
					+ imageName);
			Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			return saveCategory;
		}
	
		return null;
		
	}

	@Override
	public List<Category> findByIsActiveTrue() {
		return categoryRepository.findByIsActiveTrue();
	}

	
	
}
