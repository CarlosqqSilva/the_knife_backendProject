package org.mindswap.springtheknife.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mindswap.springtheknife.dto.restaurant.RestaurantGetDto;
import org.mindswap.springtheknife.model.Address;
import org.mindswap.springtheknife.repository.RestaurantRepository;
import org.mindswap.springtheknife.service.restaurant.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class RestaurantControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    RestaurantRepository restaurantRepository;
    @MockBean
    RestaurantServiceImpl restaurantService;

    @Test
    @AfterEach
    void init() {
        restaurantRepository.deleteAll();
        restaurantRepository.resetId();
    }


    @Test
    @DisplayName("Test get all restaurants when database has 1 user")
    void testGetAllRestaurantsWhenDatabaseHas1User() throws Exception {

        List<RestaurantGetDto> restaurants = new ArrayList<>();
        restaurants.add(new RestaurantGetDto("Porto", "Pizza", "pizza@ge.com", new Address(), "+351219879876", 0.0, new HashSet<>()));


        when(restaurantService.getAllRestaurants(0, 5, "name")).thenReturn(restaurants);


        mockMvc.perform(get("/api/v1/restaurants/")
                        .param("pageNumber", "0")
                        .param("pageSize", "5")
                        .param("sortBy", "name"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Pizza")));
    }

    @Test
    @DisplayName("Test delete a restaurant by id")
    void testDeleteRestaurantById() throws Exception {

        doNothing().when(restaurantService).deleteRestaurant(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/restaurants/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Restaurant with id 1 deleted successfully."));
    }
}
