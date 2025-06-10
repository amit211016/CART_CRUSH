package com.ecom.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;
import com.ecom.service.CommonService;
import com.ecom.service.ProductService;



@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CommonService commonService;
	
	//to Save the product
	@Override
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}
	
	//to Get All The Product
	@Override
	public List<Product> getAllProduct(){
		return productRepository.findAll();
	}
	
	//to Delete The Product
	@Override
	public boolean deleteProduct(int id) {
		Product product = productRepository.findById(id).orElse(null);
		if(!ObjectUtils.isEmpty(product)) {
			productRepository.delete(product);
			return true;
		}
		return false;
	}

	
	//to Get Product
	@Override
	public Product getProductById(int id) {		
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public Product updateProduct(Product product, MultipartFile image) {
		Product oldProduct = getProductById(product.getId());
		
		if(ObjectUtils.isEmpty(oldProduct))
		return null;
			
		String imageName = image.isEmpty() ? oldProduct.getImageName() : image.getOriginalFilename();
		product.setImageName(imageName);
		product.setDiscountPrice(commonService.calculateDiscountPrice(product.getDiscount(), product.getPrice()));
		Product updateProduct = productRepository.save(product);

		if (!ObjectUtils.isEmpty(updateProduct)) {

			if (!image.isEmpty()) {

				try {
					File saveFile = new ClassPathResource("static/img").getFile();

					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
							+ imageName);
					Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return product;
		}
		return null;
	}

	@Override
	public Product saveProduct(Product product, MultipartFile image) throws IOException {
		
		String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
		product.setImageName(imageName);
		product.setDiscountPrice(commonService.calculateDiscountPrice(product.getDiscount(), product.getPrice()));
		Product saveProduct = saveProduct(product);
		
		if(!ObjectUtils.isEmpty(saveProduct)) {
			File saveFile = new ClassPathResource("static/img").getFile();
			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
					+ imageName);
			Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			return saveProduct;
		}
	
		return null;
	}

	
}
