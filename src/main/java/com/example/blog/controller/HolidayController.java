package com.example.blog.controller;

import com.example.blog.dto.HolidayResponse;
import com.example.blog.service.HolidayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/holidays")
public class HolidayController {

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping
    public ResponseEntity<List<HolidayResponse>> getHolidays(
            @RequestParam String country,
            @RequestParam String subdivision,
            @RequestParam(defaultValue = "EN") String lang,
            @RequestParam String from,
            @RequestParam String to) {

        List<HolidayResponse> holidays = holidayService.getSchoolHolidays(country, subdivision, lang, from, to);
        return ResponseEntity.ok(holidays);
    }
}

