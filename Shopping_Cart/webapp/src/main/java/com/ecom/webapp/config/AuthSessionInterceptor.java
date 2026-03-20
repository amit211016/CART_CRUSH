package com.ecom.webapp.config;

import com.ecom.common.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Blocks access to protected MVC endpoints when no authenticated user is present in the HTTP session.
 */
public class AuthSessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/signin");
            return false;
        }
        Object userObj = session.getAttribute("user");
        Object token = session.getAttribute("token");
        if (userObj instanceof UserDto user && token instanceof String && !((String) token).isEmpty()) {
            session.setAttribute("user", user); // refresh for consistency
            return true;
        }
        response.sendRedirect("/signin");
        return false;
    }
}
