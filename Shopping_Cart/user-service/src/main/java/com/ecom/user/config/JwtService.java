package com.ecom.user.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtService {

    @Value("${security.jwt.ttl-seconds:3600}")
    private String ttlSeconds;

    // Hardcoded Base64 RSA Private Key for seamless project execution
    private static final String RSA_PRIVATE_KEY = 
        "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCWQO5E+uQHLn38763b4ikRwIXpwleOX" +
        "GZRpxpGYhLCQu3LcDIQQccS66wnQokpRk8hwR0llgWmT8A2WM0SdCwSBrxbYfhcvPCc7wXDVOg8yqAY8w" +
        "ZWSzNU4Zu8cEZHMRaO3ax+UMhqyxZdRfu5VQfxIPi/6kjAyFika+KgUANag0nS9kEUyyZNAbriBrop4EL" +
        "cNjQvlViNEoIx5XGqtx5U5sYRF3vsfLL2MP8xj/P72reuvkRLrnT/suK5gOCfg6b8nvLNCanoeO3o2gRh" +
        "DsT4iQWQa7/G5K2jwUijGKj1GXnvaJ0JLpFHDFbfjOJMchw0MpfOBwX7RirQfUQIDAQABoIBAQDCOZt8b" +
        "rP0Tq/S4E/oJp7/sQxO7UfX3eXj5pA9VbYhI7n4xVf9M8y9o0D7z3F8M0+gYqUu9g1H1QeM4cWv2ZtO8a" +
        "r6XqO0PZkX7K0T8r1I3Y3c9g7nE8x6lWvH0Zc3QeFwZ6I3F5qQ1Q9z0uL5v8vQ8d4V0F4Kz4tP9C8O6Cj" +
        "qM1c7A0R9X2x3nB4U5qC6vJ3Wz3f5s4hFvH5n7dZ4v9Jz2g7qS9u5Q7b1mC8L9z2Y8G5K6C3p4I1lF1f5" +
        "gZ7C9U8P1R1uA7L9Z5H6K5x8P1CqU2v9W0yZ6fQ5I1RwqQc8E6B1s3M1B/K0P6f1Q5Z7BAoGBANL5e2iQ" +
        "yXbJ4dK2nJv5eG8p9O9o1A3a7H9y4h0p4o0DXZ3J6Q1w8Bf1Z2R7R0B5aQzE5O6U7HwF8u9F1JwQvMvA1" +
        "aT9yS5qB9Q9hZJ7vM7e8H8D8pD9LqQ7sR4X6E4b5G8V8KxN9R9z5F6vF8l4R3Z4K4B0M5N9eF7qQ8b5BA" +
        "oGBAMM7v9D5g3L0WqY6L6E2oZ5z6A2E8rZ7qg5+uA2l9K5B4c1qP6F7a1QkY0O4C5K5s3G7gQxQ3gT1r9" +
        "jP4R4f2Fz1M7S7b9B9f3Z0s3K3n5k5W0H2n4A2K9E1M7RwqX5c0DqX9eJ5h2g8p4D0lJ3O4zF8L1hX5m0" +
        "P8aT6H5qFBAoGBAJ4y5k8Bv4C4lXmG7X9N8K31s8L+j5I1B0Q9J8L5a0Q0jY5G+W4I4n5Q9f9c7E6Y8W8" +
        "F3b2E1Q5X0R3V1N7rPqf2T4y2Q8A3A4N8P9B1J3hQ2B8X2W3wF0b2Z0P5F5rQ0Uf3r1C5gX6N8Y7U5M0F" +
        "6W3D3F1rQBAoGBAI3v1h8T7W1D2B3F0E8R8q6X3S5W8Y2E8y8U4gG1dK6M0m7d5D6Z1g3tP7y2C4s3c8X" +
        "0G7jN4B8D1Z2U4G4m8O2C1L6D8H3X0r7n9D8x6X1C4P0H7g5B5E1V3x1W9O9B8P5r5Q5h7B8L6C3T1B6E" +
        "xQECgYEAw8QY4P5L3S0L8e7eR5P0p2N5n2I5a0W3d3T1Q8H0q4g6Y7j0K3e7W2U3W1V3p3O0E2P8A8C7O" +
        "D4N8T6L0D0J4d6J8b1E2F0M0s2J5N9K5E7I7N0k0L7C3C6z0P5g9D5W2C0y1d5Z9F9k4O0I=";

    private PrivateKey getPrivateKey() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(RSA_PRIVATE_KEY);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load RSA Private Key", e);
        }
    }

    public long getTtlSeconds() {
        try {
            return Long.parseLong(ttlSeconds);
        } catch (Exception ex) {
            return 3600L;
        }
    }

    public String generateToken(String subject, Map<String, Object> claims) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(getTtlSeconds());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }
}

