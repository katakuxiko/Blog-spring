package com.example.blog.service;

import com.example.blog.dto.HolidayResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class HolidayService {

    private final RestTemplate restTemplate;

    public HolidayService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public List<HolidayResponse> getSchoolHolidays(String countryIso, String subdivisionCode,
                                                   String lang, String from, String to) {

        String url = String.format("https://openholidaysapi.org/SchoolHolidays?countryIsoCode=%s&subdivisionCode=%s&languageIsoCode=%s&validFrom=%s&validTo=%s",
                countryIso, subdivisionCode, lang, from, to);

        ResponseEntity<HolidayResponse[]> response = restTemplate.getForEntity(url, HolidayResponse[].class);

        return response.getStatusCode().is2xxSuccessful() && response.getBody() != null
                ? Arrays.asList(response.getBody())
                : Collections.emptyList();
    }
}

