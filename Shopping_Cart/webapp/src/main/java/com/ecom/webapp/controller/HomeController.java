package com.ecom.webapp.controller;

import com.ecom.common.dto.AuthRequest;
import com.ecom.common.dto.AuthResponse;
import com.ecom.common.dto.CategoryDto;
import com.ecom.common.dto.ProductDto;
import com.ecom.common.dto.UserDto;
import com.ecom.webapp.client.CatalogClient;
import com.ecom.webapp.client.UserClient;
import com.ecom.webapp.client.payload.RegisterRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CatalogClient catalogClient;

    @Autowired
    private UserClient userClient;

    @GetMapping("/")
    public String index(Model model) {
        List<CategoryDto> categories = catalogClient.categories();
        List<ProductDto> products = catalogClient.products(null);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("paramValue", "");
        return "index";
    }

    @GetMapping("/products")
    public String products(@RequestParam(value = "category", required = false) String category, Model model) {
        List<CategoryDto> categories = catalogClient.categories();
        List<ProductDto> products = catalogClient.products(category);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("paramValue", category == null ? "" : category);
        return "product";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable Integer id, Model model) {
        model.addAttribute("product", catalogClient.product(id));
        return "view_product";
    }

    @GetMapping("/signin")
    public String signin(HttpSession session) {
        session.removeAttribute("errorMsg");
        session.removeAttribute("succMsg");
        return "login";
    }

    @GetMapping("/login")
    public String loginGet() {
        // Prevent direct GET /login 400s; send to signin page
        return "redirect:/signin";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        AuthRequest req = new AuthRequest();
        req.setEmail(email);
        req.setPassword(password);
        try {
            AuthResponse response = userClient.login(req);
            session.setAttribute("token", response.getToken());
            session.setAttribute("user", response.getUser());
            return "redirect:/";
        } catch (Exception ex) {
            session.setAttribute("errorMsg", "Invalid credentials");
            return "redirect:/signin";
        }
    }

    @GetMapping("/register")
    public String register(HttpSession session) {
        session.removeAttribute("errorMsg");
        session.removeAttribute("succMsg");
        return "register";
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String registerUser(@RequestParam String name,
                               @RequestParam String mobileNo,
                               @RequestParam String email,
                               @RequestParam String address,
                               @RequestParam String city,
                               @RequestParam String state,
                               @RequestParam String pinCode,
                               @RequestParam String password,
                               @RequestParam(value = "img", required = false) MultipartFile img,
                               HttpSession session) {
        UserDto dto = new UserDto();
        dto.setName(name);
        dto.setMobileNo(mobileNo);
        dto.setEmail(email);
        dto.setAddress(address);
        dto.setCity(city);
        dto.setState(state);
        dto.setPinCode(pinCode);
        RegisterRequest request = new RegisterRequest();
        request.setUser(dto);
        request.setPassword(password);
        request.setImageName(img != null ? img.getOriginalFilename() : null);
        try {
            userClient.registerJson(request);
            session.setAttribute("succMsg", "Registered successfully. Please sign in.");
            return "redirect:/signin";
        } catch (Exception ex) {
            session.setAttribute("errorMsg", "Registration failed: " + ex.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
