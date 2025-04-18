package com.example.blog.config;

import com.example.blog.interceptor.RateLimitInterceptor;
import com.example.blog.service.RateLimitService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final RateLimitService rateLimitService;

    public WebMvcConfig(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimitInterceptor(rateLimitService))
                .addPathPatterns("/**") // Применяем ко всем эндпоинтам
                .excludePathPatterns("/error"); // Исключаем эндпоинт error
    }
} 
