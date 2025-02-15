package com.example.blog.service;

import com.example.blog.dto.RegisterRequest;
import com.example.blog.dto.AuthResponse;
import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        // ✅ Проверяем, существует ли уже такой пользователь
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }

    public AuthResponse authenticate(RegisterRequest request) {
        // 🔹 Проверяем, существует ли пользователь
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());

        if (userDetails == null) {
            throw new UsernameNotFoundException("Пользователь не найден!");
        }

        // 🔹 Аутентифицируем пользователя
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 🔹 Генерируем JWT-токен
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }
}
