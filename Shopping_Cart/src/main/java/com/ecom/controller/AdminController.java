package com.ecom.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.ecom.service.CommonService;
import com.ecom.service.ProductService;
import com.ecom.service.UserDtlsService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserDtlsService userDtlsService;
	
	@Autowired
	private CommonService commonService;
	
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
	
	@GetMapping(value = "/")
	public String index() {
		return "admin/index";
	}
	
	@GetMapping(value = "/loadAddProduct")
	public String loadAddProduct(Model model) {
		List<Category> category = categoryService.getAllCategory();
		model.addAttribute("category", category);
		return "admin/add_product";
	}
	
	@GetMapping(value = "/category")
	public String category(Model model) {
		model.addAttribute("categorys", categoryService.getAllCategory());
		return "admin/category";
	}
    
	@PostMapping(value="/saveProduct")
	public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException{
		
		if(!ObjectUtils.isEmpty(product)) {
			if(product.getDiscount()<0 || product.getDiscount()>100)
				session.setAttribute("errorMsg", "Invalid Discount!");
			else {
				Product saveProduct = productService.saveProduct(product, file);
				if(ObjectUtils.isEmpty(saveProduct)) {
					session.setAttribute("errorMsg", "Product Not Saved!");
				}else {
					session.setAttribute("succMsg", "Product Saved Successfully");
				}
			}
		}else {
			session.setAttribute("errorMsg", "Product Not Saved!");
		}
		return "redirect:/admin/loadAddProduct";
	}
	
	
	//@RequestParam ("file") MultipartFile file : to get the file in input  
	
	@PostMapping(value="/saveCategory")
	public String saveCategory(@ModelAttribute Category category , @RequestParam ("file") MultipartFile file ,
			HttpSession session) throws IOException {
		
		
		if(!ObjectUtils.isEmpty(category)) {
			if(categoryService.existsByName(category.getName())) {
				session.setAttribute("errorMsg", "Category Already exist!");
				return "redirect:/admin/category";
			}
			
				
			Category saveCategory = categoryService.saveCategory(category, file);
			if(ObjectUtils.isEmpty(saveCategory)) {
				session.setAttribute("errorMsg", "Category Not Saved!");
			}else {
				session.setAttribute("succMsg", "Category Saved Successfully");
			}
		}else {
			session.setAttribute("errorMsg", "Category Not Saved!");
		}
			return "redirect:/admin/category";
	}
	
	@GetMapping(value = "/deleteCategory/{id}")
	public String deleteCategory(@PathVariable int id, HttpSession session) {
		boolean deleteCategory = categoryService.deleteCategory(id);
		if(deleteCategory) {
			session.setAttribute("succMsg","Deleted Successfully!");
		}else session.setAttribute("errorMsg", "Failed due To Some internal issue");
		return "redirect:/admin/category";
	}
	
	@GetMapping(value = "/loadEditCategory/{id}")
	public String loadEditCategory(@PathVariable int id, Model model) {
		Category category = categoryService.getCategoryById(id);
		model.addAttribute("category",category);
		return "admin/edit_category";
	}
	
	@PostMapping(value="/updateCategory")
	public String updateCategory(@ModelAttribute Category category , @RequestParam("file") MultipartFile file , HttpSession session) throws IOException {
		Category updateCategory = categoryService.updateCategory(category, file);
		if (!ObjectUtils.isEmpty(updateCategory)) {
		    session.setAttribute("succMsg", "Category updated successfully");
		} else {
		    session.setAttribute("errorMsg", "Category not updated due to internal error");
		}

		return "redirect:/admin/loadEditCategory/"+category.getId();
	}
	
	//Poducts
	@GetMapping(value="/products")
	public String loadViewProduct(Model m) {
		List<Product> products = productService.getAllProduct();
		m.addAttribute("products", products);
		return "/admin/products";
	}
	
	
	//to Delete Product
	@GetMapping(value="/deleteProduct/{id}")
	public String deletePoduct(@PathVariable int id, HttpSession session) {
		boolean deleteProduct = productService.deleteProduct(id);
		if(deleteProduct) {
			session.setAttribute("succMsg", "Deleted Successfully");
		}else {
			session.setAttribute("errorMsg", "Not Deleted Due To Internal Error!");
		}
		return "redirect:/admin/products";
	}
	
	//to Load Product To Be Update
	@GetMapping(value="/loadEditProduct/{id}")
	public String loadEditProduct(@PathVariable("id") int id, Model m) {
		Product loadProduct = productService.getProductById(id);
		m.addAttribute("product", loadProduct);
		return "/admin/edit_product";
	}
	
	
	//to Update Product
	@PostMapping(value="/updateProduct")
	public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
		
		if (product.getDiscount()<0 || product.getDiscount()>100) {
		    session.setAttribute("errorMsg", "Invalid Discount!");
		    return "redirect:/admin/products";
		}else {
			Product updateProduct = productService.updateProduct(product, file);
			if(ObjectUtils.isEmpty(updateProduct)) {
				session.setAttribute("errorMsg", "Product Not Update due To Internal Server error!");
			}else
				session.setAttribute("succMsg", "Product Updated Successfully");
		}

		return "redirect:/admin/products";
	}
	
	@GetMapping(value = "/getAllUser")
	public String getAllUsers(Model m) {
		List<UserDtls> allUser = userDtlsService.getAllUsersOfUserRole("Role_user");
		System.out.println(allUser);
		m.addAttribute("users", allUser);
		return "admin/users";
	}
	
	@GetMapping("/updateUserStatus")
	public String updateAccountStatus(@RequestParam Boolean status, @RequestParam Integer id, HttpSession session) {
		Boolean f = userDtlsService.updateAccountStatus(id, status);
		
		if(f) {
			session.setAttribute("succMsg", "Status Updated Successfully");
		}else session.setAttribute("errorMsg", "Something went Wrong on server!");
		return "redirect:/admin/getAllUser";
	}
	
}
