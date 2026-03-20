package com.ecom.webapp.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestClientConfig {

    @Bean
    public SessionTokenInterceptor sessionTokenInterceptor() {
        return new SessionTokenInterceptor();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, SessionTokenInterceptor interceptor) {
        RestTemplate restTemplate = builder.build();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(restTemplate.getInterceptors());
        interceptors.add(interceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
