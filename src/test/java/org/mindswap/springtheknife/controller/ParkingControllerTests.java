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
public class ParkingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetParkingData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parking/checkParking"))
                .andExpect(status().isOk());
    }
}
