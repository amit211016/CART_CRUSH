package com.ecom.gateway.config;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthWebFilter implements WebFilter {

    @Autowired
    private JwtValidator jwtValidator;

    private final List<String> excludedUrls = List.of("/auth/login", "/auth/register", "/actuator");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        
        // 1. PUBLIC ENDPOINTS (No token required)
        if (excludedUrls.stream().anyMatch(path::contains)) {
            return chain.filter(exchange);
        }
        
        // 2. PUBLIC CATALOG ENDPOINT (Only GET requests to catalog)
        if (path.startsWith("/catalog") && HttpMethod.GET.equals(request.getMethod())) {
            return chain.filter(exchange);
        }

        // 3. SECURED ENDPOINTS (Token required)
        String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Claims claims = jwtValidator.parseClaims(token);
                String subject = claims.getSubject();
                String role = claims.get("role", String.class);

                // Mutate the request to add custom downstream headers
                ServerHttpRequest mutatedRequest = request.mutate()
                        .header("X-User-Id", subject != null ? subject : "")
                        .header("X-User-Role", role != null ? role : "")
                        .build();

                ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                return chain.filter(mutatedExchange);
                
            } catch (Exception ex) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }
        
        // Block request if no valid token
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
