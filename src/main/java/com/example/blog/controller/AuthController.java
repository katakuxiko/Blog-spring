package com.example.blog.controller;

import com.example.blog.dto.AuthResponse;
import com.example.blog.dto.RegisterRequest;
import com.example.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class AuthController {
    private final AuthService authService;

    @ResponseStatus(HttpStatus.OK) // ✅ Явно указываем 200 OK
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody RegisterRequest request) {
        return authService.authenticate(request);
    }
}
