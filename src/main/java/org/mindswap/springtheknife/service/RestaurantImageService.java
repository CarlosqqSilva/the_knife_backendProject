package org.mindswap.springtheknife.service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RestaurantImageService {

    private static final String API_URL = "http://127.0.0.1:7860/sdapi/v1/txt2img";
    private static final Integer STEPS = 15;

    public static String getImageDataFromAPI(String prompt) {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> payload = new HashMap<>();
        payload.put("prompt", prompt);
        payload.put("steps", STEPS);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, Map.class);
        String resultString = Objects.requireNonNull(response.getBody()).get("images").toString().replaceAll("[\\[\\]]", "");

        return resultString;
    }
}
