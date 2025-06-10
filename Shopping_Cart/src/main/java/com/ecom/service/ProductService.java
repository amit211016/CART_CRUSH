package com.ecom.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Category;
import com.ecom.model.Product;

public interface ProductService {
	
	public abstract Product saveProduct(Product product);
	
	public abstract List<Product> getAllProduct();
	
	public abstract boolean deleteProduct(int id);
	
	public abstract Product getProductById(int id);
	
	public abstract Product updateProduct(Product product, MultipartFile file);
	
	public abstract Product saveProduct(Product product, MultipartFile file) throws IOException;

	
}
