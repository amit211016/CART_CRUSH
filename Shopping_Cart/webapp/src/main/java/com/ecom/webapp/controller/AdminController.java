package com.ecom.webapp.controller;

import com.ecom.common.dto.CategoryDto;
import com.ecom.common.dto.ProductDto;
import com.ecom.webapp.client.CatalogClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CatalogClient catalogClient;

    @GetMapping
    public String dashboard() {
        return "admin/index";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        List<CategoryDto> categories = catalogClient.allCategories();
        model.addAttribute("categories", categories);
        return "admin/category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@RequestParam String name,
                               @RequestParam(defaultValue = "true") boolean isActive,
                               @RequestParam(value = "file", required = false) MultipartFile file,
                               HttpSession session) {
        try {
            CategoryDto dto = new CategoryDto();
            dto.setName(name);
            dto.setActive(isActive);
            dto.setImageName(file != null && !file.isEmpty() ? file.getOriginalFilename() : "mobile.png");
            catalogClient.createCategory(dto);
            session.setAttribute("succMsg", "Category saved");
        } catch (Exception ex) {
            session.setAttribute("errorMsg", "Failed: " + ex.getMessage());
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable Integer id, HttpSession session) {
        try {
            catalogClient.deleteCategory(id);
            session.setAttribute("succMsg", "Category deleted");
        } catch (Exception ex) {
            session.setAttribute("errorMsg", "Failed: " + ex.getMessage());
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", catalogClient.adminProducts());
        return "admin/products";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("categories", catalogClient.categories());
        return "admin/add_product";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@RequestParam String title,
                              @RequestParam String description,
                              @RequestParam String category,
                              @RequestParam Double price,
                              @RequestParam Integer discount,
                              @RequestParam Integer stock,
                              @RequestParam(defaultValue = "true") boolean isActive,
                              @RequestParam(value = "file", required = false) MultipartFile file,
                              HttpSession session) {
        try {
            ProductDto dto = new ProductDto();
            dto.setTitle(title);
            dto.setDescription(description);
            dto.setCategoryName(category);
            dto.setPrice(price);
            dto.setDiscount(discount);
            dto.setDiscountPrice(price - (price * discount / 100.0));
            dto.setStock(stock);
            dto.setActive(isActive);
            dto.setImageName(file != null && !file.isEmpty() ? file.getOriginalFilename() : "laptop.jpg");
            catalogClient.createProduct(dto);
            session.setAttribute("succMsg", "Product saved");
        } catch (Exception ex) {
            session.setAttribute("errorMsg", "Failed: " + ex.getMessage());
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Integer id, HttpSession session) {
        try {
            catalogClient.deleteProduct(id);
            session.setAttribute("succMsg", "Product deleted");
        } catch (Exception ex) {
            session.setAttribute("errorMsg", "Failed: " + ex.getMessage());
        }
        return "redirect:/admin/products";
    }
}
