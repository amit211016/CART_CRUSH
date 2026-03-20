package com.ecom.common.dto;

public class UserDto {
    private Integer id;
    private String name;
    private String mobileNo;
    private String email;
    private String address;
    private String state;
    private String city;
    private String pinCode;
    private String role;
    private String imageName;
    private Boolean enabled;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getPinCode() { return pinCode; }
    public void setPinCode(String pinCode) { this.pinCode = pinCode; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
}
