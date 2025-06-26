package com.ecom.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.UserDtls;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
import com.ecom.service.UserDtlsService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserDtlsService userDtlsService;
	
	@ModelAttribute
	public void getUsersLoginDetail(Principal p, Model m) {
		if(p!=null) {
			String email = p.getName();
			UserDtls userDtls = userDtlsService.findByEmail(email);
			m.addAttribute("user", userDtls);
		}
			
		List<Category> categories = categoryService.findByIsActiveTrue();
		m.addAttribute("categories", categories);
	}
	
	@GetMapping(value="/")
	public String index() {
		return "index";
	}
	
	@GetMapping(value="/signin")
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
	
	@PostMapping(value="/saveUser")
	public String saveUser(@ModelAttribute UserDtls userDtls, @RequestParam("img") MultipartFile file, HttpSession session) {
		if(!ObjectUtils.isEmpty(userDtls)) {
			UserDtls saveUserDtls = userDtlsService.saveUser(userDtls, file);
			if(!ObjectUtils.isEmpty(saveUserDtls)) {
				session.setAttribute("succMsg", "User Registered Successfully");
				return "redirect:/register" ;
			}
		}
		session.setAttribute("errorMsg", "Not Registered Due To Internal Server Error!");
		return "redirect:/register" ;
	}
}
