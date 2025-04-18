package com.example.blog.interceptor;

import com.example.blog.service.RateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimitService rateLimitService;

    public RateLimitInterceptor(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientId = getClientIdentifier(request);
        
        if (!rateLimitService.tryConsume(clientId)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded. Please try again later.");
            return false;
        }
        
        return true;
    }

    private String getClientIdentifier(HttpServletRequest request) {
        // Если есть JWT токен, используем ID пользователя
        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            return token;
        }
        // Иначе используем IP адрес
        return request.getRemoteAddr();
    }
} 
