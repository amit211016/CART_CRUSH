package com.ecom.catalog.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 500)
    private String description;
    @Column(length = 500)
    private String title;
    private Double price;
    private Integer stock;
    private String imageName;
    private Integer discount;
    private Double discountPrice;
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }
    public Integer getDiscount() { return discount; }
    public void setDiscount(Integer discount) { this.discount = discount; }
    public Double getDiscountPrice() { return discountPrice; }
    public void setDiscountPrice(Double discountPrice) { this.discountPrice = discountPrice; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
