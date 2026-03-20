package com.ecom.catalog.web;

import com.ecom.catalog.service.ProductService;
import com.ecom.common.dto.ProductDto;
import com.ecom.common.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Integer id, @RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "deleted"));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> all(@RequestParam(value = "category", required = false) String category) {
        if (category != null) {
            return ResponseEntity.ok(productService.byCategory(category));
        }
        return ResponseEntity.ok(productService.active());
    }

    @GetMapping("/admin")
    public ResponseEntity<List<ProductDto>> adminAll() {
        return ResponseEntity.ok(productService.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.get(id));
    }
}
