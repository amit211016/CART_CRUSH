package com.ecom.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecom.model.Category;
import com.ecom.model.UserDtls;
import com.ecom.repository.UserDtlsRepository;
import com.ecom.service.CategoryService;
import com.ecom.service.UserDtlsService;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	private UserDtlsService userDtlsService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/")
	public String home() {
		return "/user/home";
	}
	
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
}
