package com.ecom.catalog.service;

import com.ecom.catalog.model.Category;
import com.ecom.catalog.model.Product;
import com.ecom.catalog.repository.CategoryRepository;
import com.ecom.catalog.repository.ProductRepository;
import com.ecom.common.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PricingService pricingService;

    @Override
    public ProductDto create(ProductDto dto) {
        Product product = toEntity(dto);
        product.setDiscountPrice(pricingService.discountPrice(dto.getPrice(), dto.getDiscount()));
        productRepository.save(product);
        return toDto(product);
    }

    @Override
    public ProductDto update(Integer id, ProductDto dto) {
        Product product = productRepository.findById(id).orElseThrow();
        if (!ObjectUtils.isEmpty(dto.getTitle())) product.setTitle(dto.getTitle());
        if (!ObjectUtils.isEmpty(dto.getDescription())) product.setDescription(dto.getDescription());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());
        if (dto.getDiscount() != null) product.setDiscount(dto.getDiscount());
        if (dto.getStock() != null) product.setStock(dto.getStock());
        if (!ObjectUtils.isEmpty(dto.getImageName())) product.setImageName(dto.getImageName());
        if (dto.getActive() != null) product.setActive(dto.getActive());
        if (!ObjectUtils.isEmpty(dto.getCategoryName())) {
            Category category = categoryRepository.findByName(dto.getCategoryName()).orElseThrow();
            product.setCategory(category);
        }
        product.setDiscountPrice(pricingService.discountPrice(product.getPrice(), product.getDiscount()));
        productRepository.save(product);
        return toDto(product);
    }

    @Override
    public void delete(Integer id) { productRepository.deleteById(id); }

    @Override
    public List<ProductDto> byCategory(String categoryName) {
        return productRepository.findByCategory_NameAndActiveTrue(categoryName)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> active() {
        return productRepository.findByActiveTrue().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> all() {
        return productRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto get(Integer id) {
        return productRepository.findById(id).map(this::toDto).orElse(null);
    }

    private ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setDiscount(product.getDiscount());
        dto.setDiscountPrice(product.getDiscountPrice());
        dto.setStock(product.getStock());
        dto.setImageName(product.getImageName());
        dto.setActive(product.getActive());
        dto.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : null);
        return dto;
    }

    private Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDiscount(dto.getDiscount());
        product.setDiscountPrice(dto.getDiscountPrice());
        product.setStock(dto.getStock());
        product.setImageName(dto.getImageName());
        product.setActive(dto.getActive() != null ? dto.getActive() : Boolean.TRUE);
        if (!ObjectUtils.isEmpty(dto.getCategoryName())) {
            Category category = categoryRepository.findByName(dto.getCategoryName())
                    .orElseThrow(() -> new IllegalArgumentException("category not found"));
            product.setCategory(category);
        }
        return product;
    }
}
