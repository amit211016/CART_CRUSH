package com.ecom.webapp.client;

import com.ecom.common.dto.CategoryDto;
import com.ecom.common.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CatalogClient {

    @Value("${services.catalog.base-url}")
    private String catalogBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    public List<ProductDto> products(String category) {
        String url = catalogBaseUrl + "/products" + (category != null ? "?category=" + category : "");
        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ProductDto>>() {});
        return response.getBody();
    }

    public ProductDto product(Integer id) {
        return restTemplate.getForObject(catalogBaseUrl + "/products/" + id, ProductDto.class);
    }

    public List<CategoryDto> categories() {
        ResponseEntity<List<CategoryDto>> response = restTemplate.exchange(
                catalogBaseUrl + "/categories/active", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CategoryDto>>() {});
        return response.getBody();
    }

    public List<CategoryDto> allCategories() {
        ResponseEntity<List<CategoryDto>> response = restTemplate.exchange(
                catalogBaseUrl + "/categories", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CategoryDto>>() {});
        return response.getBody();
    }

    public CategoryDto createCategory(CategoryDto dto) {
        return restTemplate.postForObject(catalogBaseUrl + "/categories", dto, CategoryDto.class);
    }

    public void deleteCategory(Integer id) {
        restTemplate.delete(catalogBaseUrl + "/categories/" + id);
    }

    public List<ProductDto> adminProducts() {
        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                catalogBaseUrl + "/products/admin", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ProductDto>>() {});
        return response.getBody();
    }

    public ProductDto createProduct(ProductDto dto) {
        return restTemplate.postForObject(catalogBaseUrl + "/products", dto, ProductDto.class);
    }

    public void deleteProduct(Integer id) {
        restTemplate.delete(catalogBaseUrl + "/products/" + id);
    }
}
