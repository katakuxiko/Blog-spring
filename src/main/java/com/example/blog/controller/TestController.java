package com.example.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/error")
    public void triggerError() {
        logger.debug("Вызван эндпоинт /api/test/error");
        logger.error("Тестирование логирования ошибки 500: Симуляция серверной ошибки");
        throw new RuntimeException("Тестовая ошибка 500");
    }

    @GetMapping("/warn")
    public ResponseEntity<String> triggerWarning() {
        logger.debug("Вызван эндпоинт /api/test/warn");
        logger.warn("Тестирование предупреждения: Обнаружена потенциальная проблема");
        return ResponseEntity.ok("Предупреждение!");
    }

    @GetMapping("/info")
    public ResponseEntity<String> triggerInfo() {
        logger.debug("Вызван эндпоинт /api/test/info");
        logger.info("Тестирование информационного сообщения: Операция выполнена успешно");
        return ResponseEntity.ok("Информация выведена в консоль");
    }
} 
