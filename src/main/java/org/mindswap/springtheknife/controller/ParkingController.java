package org.mindswap.springtheknife.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mindswap.springtheknife.model.Parking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {

    private final WebClient webClient;
    private ObjectMapper objectMapper;

    @Autowired
    public ParkingController(ObjectMapper objectMapper) {
        this.webClient = WebClient.create();
        this.objectMapper = objectMapper;
    }

    @GetMapping("/checkParking")
    public Mono<List<Parking.Record>> getParkingData() {

        String url = "http://localhost:8081/parking";

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .map(body -> {
                    try {
                        Parking parking = objectMapper.readValue(body, Parking.class);
                        return parking.getResult().getRecords();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
