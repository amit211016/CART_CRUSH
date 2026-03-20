package com.ecom.catalog.web;

import com.ecom.catalog.service.CategoryService;
import com.ecom.common.dto.CategoryDto;
import com.ecom.common.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Integer id, @RequestBody CategoryDto dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "deleted"));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> all() {
        return ResponseEntity.ok(categoryService.all());
    }

    @GetMapping("/active")
    public ResponseEntity<List<CategoryDto>> active() {
        return ResponseEntity.ok(categoryService.active());
    }
}
