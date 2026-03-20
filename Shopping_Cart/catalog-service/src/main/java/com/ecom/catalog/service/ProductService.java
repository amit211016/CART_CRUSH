package com.ecom.catalog.service;

import com.ecom.common.dto.ProductDto;
import java.util.List;

public interface ProductService {
    ProductDto create(ProductDto dto);
    ProductDto update(Integer id, ProductDto dto);
    void delete(Integer id);
    List<ProductDto> byCategory(String categoryName);
    List<ProductDto> active();
    List<ProductDto> all();
    ProductDto get(Integer id);
}
