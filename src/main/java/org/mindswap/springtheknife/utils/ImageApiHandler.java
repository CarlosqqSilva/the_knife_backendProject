package org.mindswap.springtheknife.utils;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ImageApiHandler {

    private static final String API_URL = "http://127.0.0.1:7860/sdapi/v1/txt2img";

    private static final Integer STEPS = 15; //Amount of steps used to generate the image 15/25 should be good enough

    public static String getImageDataFromAPI(String prompt) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> payload = new HashMap<>();
        payload.put("prompt", prompt);
        payload.put("steps", STEPS);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, Map.class);

        JSONObject json = new JSONObject(response.getBody());

        return json.getJSONArray("images").getString(0);
    }
}
