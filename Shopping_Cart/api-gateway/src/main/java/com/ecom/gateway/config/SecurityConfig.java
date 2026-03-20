package com.ecom.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // In our architecture, the Gateway ONLY does Authentication (via JwtAuthWebFilter)
        // and Authorization is deferred to the downstream microservices via Headers.
        // Therefore, we disable the internal Spring WebFlux Security checks here.
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex
                        .anyExchange().permitAll()
                )
                .build();
    }
}

