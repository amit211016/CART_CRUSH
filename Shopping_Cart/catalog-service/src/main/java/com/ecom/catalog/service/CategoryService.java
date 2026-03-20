package com.ecom.catalog.service;

import com.ecom.common.dto.CategoryDto;
import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryDto dto);
    CategoryDto update(Integer id, CategoryDto dto);
    void delete(Integer id);
    List<CategoryDto> active();
    List<CategoryDto> all();
}
