package com.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;

@Controller

public class HomeController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	
	
	@GetMapping(value="/")
	public String index() {
		return "index";
	}
	
	@GetMapping(value="/login")
	public String login() {
		return "login";
	}
	
	@GetMapping(value="/register") 
	public String register() {
		return "register";
	}
	
	@GetMapping(value="/products") 
	public String products(Model m, @RequestParam(value="category", defaultValue = "") String category) {
		List<Category> categoryList = categoryService.findByIsActiveTrue();
		List<Product> productList = productService.findProduct(category);
		m.addAttribute("categories", categoryList);
		m.addAttribute("products", productList);
		m.addAttribute("paramValue", category);
		return "product";
	}
	
	@GetMapping(value="/product/{id}") 
	public String view_product(Model m, @PathVariable("id") int id) {
		m.addAttribute("product", productService.getProductById(id));
		return "view_product";
	}
}
