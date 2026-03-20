package com.ecom.common.dto;

public class CategoryDto {
    private Integer id;
    private String name;
    private String imageName;
    private Boolean active;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
