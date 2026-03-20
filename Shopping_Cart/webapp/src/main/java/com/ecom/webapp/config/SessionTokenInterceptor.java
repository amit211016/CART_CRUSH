package com.ecom.webapp.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

/**
 * Propagates the JWT stored in the user's HTTP session to downstream service calls.
 */
public class SessionTokenInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpSession session = attrs.getRequest().getSession(false);
            if (session != null) {
                Object token = session.getAttribute("token");
                if (token instanceof String s && StringUtils.hasText(s)) {
                    request.getHeaders().setBearerAuth(s);
                }
            }
        }
        return execution.execute(request, body);
    }
}
