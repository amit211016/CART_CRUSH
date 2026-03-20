package com.ecom.user.service;

import com.ecom.common.dto.AuthRequest;
import com.ecom.common.dto.AuthResponse;
import com.ecom.common.dto.UserDto;

public interface UserAccountService {
    UserDto register(UserDto userDto, String rawPassword, String imageName);
    AuthResponse authenticate(AuthRequest request);
    UserDto findByEmail(String email);
    void unlockIfExpired(UserDto userDto);
}
