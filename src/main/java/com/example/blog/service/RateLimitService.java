package com.example.blog.service;

import com.example.blog.config.RateLimitConfig;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    private final RateLimitConfig rateLimitConfig;

    public RateLimitService(RateLimitConfig rateLimitConfig) {
        this.rateLimitConfig = rateLimitConfig;
    }

    public Bucket resolveBucket(String key) {
        return buckets.computeIfAbsent(key, k -> rateLimitConfig.createNewBucket());
    }

    public boolean tryConsume(String key) {
        return resolveBucket(key).tryConsume(1);
    }
} 
