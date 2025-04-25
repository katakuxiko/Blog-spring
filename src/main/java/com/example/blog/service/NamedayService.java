package com.example.blog.service;

import com.example.blog.dto.NamedayResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class NamedayService {

    private final RestTemplate restTemplate;

    public NamedayService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Map<String, String> getTodayNamedays(String timezone) {
        String url = "https://nameday.abalin.net/api/V2/today/" + timezone;
        ResponseEntity<NamedayResponse> response = restTemplate.getForEntity(url, NamedayResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getData();
        }

        return Collections.emptyMap();
    }
}

