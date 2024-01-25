package org.mindswap.springtheknife.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    private final WebClient webClient;

    public WeatherController() {
        this.webClient = WebClient.create();
    }

    @GetMapping("/currentWeather/")
    public Mono<String> getCurrentWeatherData(@RequestParam("location") String location) {
        String url = "http://localhost:8081/currentWeather/currentWeatherResource?location=" + location;


        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Failed : HTTP error code : " + response.statusCode())))
                .bodyToMono(String.class);
    }
}
