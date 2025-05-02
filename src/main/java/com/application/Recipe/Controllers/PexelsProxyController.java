package com.application.Recipe.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/pexels")
//@CrossOrigin(origins = "*")
public class PexelsProxyController {

    @Value("${pexels.api.key}")
    private String apiKey;

    private final String PEXELS_API_URL = "https://api.pexels.com/v1/search";

    @GetMapping
    public ResponseEntity<String> searchImages(@RequestParam String query, @RequestParam(defaultValue = "1") int perPage) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = PEXELS_API_URL + "?query=" + query + "&per_page=" + perPage;

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching from Pexels API");
        }
    }
}