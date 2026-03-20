package com.ecom.catalog.service;

import  com.ecom.catalog.model.Category;
import com.ecom.catalog.repository.CategoryRepository;
import com.ecom.common.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Override
    public CategoryDto create(CategoryDto dto) {
        if (repository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Category already exists");
        }
        Category category = toEntity(dto);
        category.setActive(Boolean.TRUE.equals(dto.getActive()) ? dto.getActive() : true);
        repository.save(category);
        return toDto(category);
    }

    @Override
    public CategoryDto update(Integer id, CategoryDto dto) {
        Category category = repository.findById(id).orElseThrow();
        category.setName(dto.getName());
        if (!ObjectUtils.isEmpty(dto.getImageName())) {
            category.setImageName(dto.getImageName());
        }
        if (dto.getActive() != null) {
            category.setActive(dto.getActive());
        }
        repository.save(category);
        return toDto(category);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<CategoryDto> active() {
        return repository.findByActiveTrue().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> all() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    private CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setImageName(category.getImageName());
        dto.setActive(category.getActive());
        return dto;
    }

    private Category toEntity(CategoryDto dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setImageName(dto.getImageName());
        category.setActive(dto.getActive());
        return category;
    }
}
