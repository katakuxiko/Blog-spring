package com.example.blog.controller;

import com.example.blog.service.NamedayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/namedays")
public class NamedayController {

    private final NamedayService namedayService;

    public NamedayController(NamedayService namedayService) {
        this.namedayService = namedayService;
    }

    @GetMapping("/{timezone}")
    public ResponseEntity<Map<String, String>> getNamedays(@PathVariable String timezone) {
        Map<String, String> namedays = namedayService.getTodayNamedays(timezone);
        return ResponseEntity.ok(namedays);
    }
}

