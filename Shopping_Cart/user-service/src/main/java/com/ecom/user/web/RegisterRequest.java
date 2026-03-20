package com.ecom.user.web;

import com.ecom.common.dto.UserDto;

public class RegisterRequest {
    private UserDto user;
    private String password;
    private String imageName;

    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }
}
