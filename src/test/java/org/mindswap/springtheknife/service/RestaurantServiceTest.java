package org.mindswap.springtheknife.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mindswap.springtheknife.converter.RestaurantConverter;
import org.mindswap.springtheknife.dto.restaurant.RestaurantGetDto;
import org.mindswap.springtheknife.dto.restaurant.RestaurantPostDto;
import org.mindswap.springtheknife.exceptions.city.CityNotFoundException;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantNotFoundException;
import org.mindswap.springtheknife.model.Address;
import org.mindswap.springtheknife.model.City;
import org.mindswap.springtheknife.model.Restaurant;
import org.mindswap.springtheknife.repository.RestaurantRepository;
import org.mindswap.springtheknife.repository.RestaurantTypeRepository;
import org.mindswap.springtheknife.service.city.CityServiceImpl;
import org.mindswap.springtheknife.service.restaurant.RestaurantServiceImpl;
import org.mindswap.springtheknife.service.restauranttype.RestaurantTypeImpl;
import org.mindswap.springtheknife.utils.Message;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class RestaurantServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private CityServiceImpl cityServiceImpl;

    @Mock
    private RestaurantTypeImpl restaurantTypeImpl;

    @Mock
    private RestaurantTypeRepository restaurantTypeRepository;

    @Mock
    private RestaurantConverter restaurantConverter;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    void deleteData() {
        restaurantRepository.deleteAll();
        restaurantRepository.resetId();
    }

    @Test
    void testGetRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        when(restaurantRepository.findAll()).thenReturn(restaurants);

        List<RestaurantGetDto> result = restaurantService.getRestaurants();

        assertEquals(restaurants.size(), result.size());
    }

    @Test
    public void testDeleteRestaurant() throws RestaurantNotFoundException {
        // Arrange
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant(); // Assuming Restaurant is your entity class

        // Mocking the repository behavior
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        doNothing().when(restaurantRepository).deleteById(restaurantId);

        // Act
        assertDoesNotThrow(() -> restaurantService.deleteRestaurant(restaurantId));
    }

    @Test
    void testDeleteRestaurantNotFound() {
        // Arrange
        Long nonExistingRestaurantId = 2L;

        // Mocking the repository behavior
        when(restaurantRepository.findById(nonExistingRestaurantId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.deleteRestaurant(nonExistingRestaurantId));
    }

    @Test
    void testAddRestaurantCityNotFound() throws CityNotFoundException {
        RestaurantPostDto restaurantPostDto = new RestaurantPostDto(
                "RestaurantName",
                new Address(/* provide address details */),
                "restaurant@example.com",
                "123456789",
                40.7128, 74.0060, // latitude, longitude
                1L, // cityId
                new HashSet<>(Set.of(1L, 2L, 3L)) // restaurantTypes
        );
        when(cityServiceImpl.getCityById(1L)).thenThrow(new CityNotFoundException("1" + Message.CITY_NOT_FOUND));

        assertThrows(CityNotFoundException.class, () -> {
            restaurantService.addRestaurant(restaurantPostDto);
        });

        // Verificações
        verify(cityServiceImpl, times(1)).getCityById(1L);
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void testAddRestaurantAlreadyExists() throws RestaurantAlreadyExistsException, CityNotFoundException {
        // Arrange
        RestaurantPostDto restaurantPostDto = new RestaurantPostDto(
                "ExistingRestaurant",
                new Address(/* provide address details */),
                "existing.restaurant@example.com", // Use um e-mail já existente para simular a existência
                "123456789",
                40.7128, 74.0060, // latitude, longitude
                1L, // cityId
                new HashSet<>(Set.of(1L, 2L, 3L)) // restaurantTypes
        );

        when(cityServiceImpl.getCityById(1L)).thenReturn(new City());
        when(restaurantRepository.findByEmail("existing.restaurant@example.com")).thenReturn(Optional.of(new Restaurant()));

        // Act and Assert
        assertThrows(RestaurantAlreadyExistsException.class, () -> {
            restaurantService.addRestaurant(restaurantPostDto);
        });

        // Verificações
        verify(cityServiceImpl, times(1)).getCityById(1L);
        verify(restaurantRepository, times(1)).findByEmail("existing.restaurant@example.com");
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void testGetById() throws CityNotFoundException, RestaurantNotFoundException {
        long restaurantId = 1L;
        Restaurant existingRestaurant = new Restaurant();

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));

        Restaurant result = restaurantService.getById(restaurantId);

        assertEquals(existingRestaurant, result);

        verify(restaurantRepository, times(1)).findById(restaurantId);

        verifyNoMoreInteractions(restaurantRepository);
    }
}
