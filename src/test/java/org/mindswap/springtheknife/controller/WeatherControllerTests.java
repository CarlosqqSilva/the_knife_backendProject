package org.mindswap.springtheknife.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {

    }

    @Test
    public void testGetCurrentWeatherData() throws Exception {
        String location = "London";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/weather/currentWeather/")
                        .param("location", location))
                .andExpect(status().isOk());
    }
}