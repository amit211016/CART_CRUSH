package com.ecom.common.dto;

public class ProductDto {
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private Integer discount;
    private Double discountPrice;
    private Integer stock;
    private String categoryName;
    private String imageName;
    private Boolean active;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getDiscount() { return discount; }
    public void setDiscount(Integer discount) { this.discount = discount; }
    public Double getDiscountPrice() { return discountPrice; }
    public void setDiscountPrice(Double discountPrice) { this.discountPrice = discountPrice; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
