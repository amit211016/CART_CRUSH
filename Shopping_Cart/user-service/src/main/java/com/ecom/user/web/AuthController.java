package com.ecom.user.web;

import com.ecom.common.dto.AuthRequest;
import com.ecom.common.dto.AuthResponse;
import com.ecom.common.dto.UserDto;
import com.ecom.common.model.ApiResponse;
import com.ecom.user.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest registerRequest) {
        UserDto userDto = registerRequest.getUser();
        userAccountService.register(userDto, registerRequest.getPassword(), registerRequest.getImageName());
        return ResponseEntity.ok(new ApiResponse(true, "registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(userAccountService.authenticate(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(@RequestParam("email") String email) {
        return ResponseEntity.ok(userAccountService.findByEmail(email));
    }
}
