package com.ecom.webapp.client;

import com.ecom.common.dto.AuthRequest;
import com.ecom.common.dto.AuthResponse;
import com.ecom.common.dto.UserDto;
import com.ecom.webapp.client.payload.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {

    @Value("${services.user.base-url}")
    private String userServiceBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    public AuthResponse login(AuthRequest request) {
        return restTemplate.postForObject(userServiceBaseUrl + "/auth/login", request, AuthResponse.class);
    }

    public void registerJson(RegisterRequest registerRequest) {
        restTemplate.postForEntity(userServiceBaseUrl + "/auth/register", registerRequest, Void.class);
    }

    public UserDto me(String email, String token) {
        ResponseEntity<UserDto> response = restTemplate.exchange(
                userServiceBaseUrl + "/auth/me?email=" + email,
                HttpMethod.GET,
                requestWithToken(token),
                UserDto.class);
        return response.getBody();
    }

    private HttpEntity<Void> requestWithToken(String token) {
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }
}
