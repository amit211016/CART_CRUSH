package com.ecom.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private SessionTokenInterceptor sessionTokenInterceptor; // keep bean managed

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Protect admin pages with role check
        registry.addInterceptor(new AdminOnlyInterceptor())
                .addPathPatterns("/admin/**");
        // Protect authenticated pages if we add more later (currently only admin)
        registry.addInterceptor(new AuthSessionInterceptor())
                .addPathPatterns("/logout");
    }
}
