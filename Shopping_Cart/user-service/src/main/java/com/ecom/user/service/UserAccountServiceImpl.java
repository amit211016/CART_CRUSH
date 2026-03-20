package com.ecom.user.service;

import com.ecom.common.dto.AuthRequest;
import com.ecom.common.dto.AuthResponse;
import com.ecom.common.dto.UserDto;
import com.ecom.common.model.Roles;
import com.ecom.user.config.JwtService;
import com.ecom.user.model.UserAccount;
import com.ecom.user.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private static final int MAX_ATTEMPTS = 3;
    private static final long LOCK_DURATION_MS = 10 * 60 * 1000L; // 10 minutes

    @Autowired
    private UserAccountRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    public UserDto register(UserDto dto, String rawPassword, String imageName) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("email already registered");
        }
        UserAccount user = new UserAccount();
        user.setName(dto.getName());
        user.setMobileNo(dto.getMobileNo());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setCity(dto.getCity());
        user.setState(dto.getState());
        user.setPinCode(dto.getPinCode());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setImageName(StringUtils.hasText(imageName) ? imageName : "default.jpg");
        user.setRole(StringUtils.hasText(dto.getRole()) ? dto.getRole() : Roles.USER);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setFailedAttempt(0);
        repository.save(user);
        return toDto(user);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserAccount user = repository.findByEmail(request.getEmail()).orElseThrow();
        resetAttemptsIfNeeded(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        String token = jwtService.generateToken(user.getEmail(), claims);
        Instant expiresAt = Instant.now().plusSeconds(jwtService.getTtlSeconds());
        return new AuthResponse(token, expiresAt, toDto(user));
    }

    @Override
    public UserDto findByEmail(String email) {
        return repository.findByEmail(email).map(this::toDto).orElse(null);
    }

    @Override
    public void unlockIfExpired(UserDto userDto) {
        if (userDto == null) return;
        repository.findByEmail(userDto.getEmail()).ifPresent(this::resetAttemptsIfNeeded);
    }

    private void resetAttemptsIfNeeded(UserAccount user) {
        if (user.getLockTime() != null) {
            long unlockAt = user.getLockTime().getTime() + LOCK_DURATION_MS;
            if (System.currentTimeMillis() > unlockAt) {
                user.setAccountNonLocked(true);
                user.setFailedAttempt(0);
                user.setLockTime(null);
                repository.save(user);
            }
        }
    }

    private UserDto toDto(UserAccount user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setMobileNo(user.getMobileNo());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        dto.setState(user.getState());
        dto.setCity(user.getCity());
        dto.setPinCode(user.getPinCode());
        dto.setRole(user.getRole());
        dto.setImageName(user.getImageName());
        dto.setEnabled(user.getEnabled());
        return dto;
    }
}
