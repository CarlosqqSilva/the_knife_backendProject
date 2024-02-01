package org.mindswap.springtheknife.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mindswap.springtheknife.converter.RestaurantTypeConverter;
import org.mindswap.springtheknife.dto.restaurantTypeDto.RestaurantTypeDto;
import org.mindswap.springtheknife.exceptions.restaurantType.RestaurantTypeAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.restaurantType.RestaurantTypeNotFoundException;
import org.mindswap.springtheknife.model.RestaurantType;
import org.mindswap.springtheknife.repository.RestaurantTypeRepository;
import org.mindswap.springtheknife.service.restauranttype.RestaurantTypeImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantTypeTests {

    @Mock
    private RestaurantTypeRepository restaurantTypeRepository;

    @InjectMocks
    private RestaurantTypeImpl restaurantTypeService;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    void deleteData() {
        restaurantTypeRepository.deleteAll();
        restaurantTypeRepository.resetId();
    }

    @Test
    public void testGetRestaurantType() {
        List<RestaurantType> types = new ArrayList<>();
        when(restaurantTypeRepository.findAll()).thenReturn(types);

        List<RestaurantTypeDto> result = restaurantTypeService.getRestaurantType();

        assertEquals(types.size(), result.size());
    }

    @Test
    void testaddType() throws RestaurantTypeAlreadyExistsException {

        RestaurantTypeDto existingTypeDto = new RestaurantTypeDto(1L, "Italian");

        when(restaurantTypeRepository.findByType(existingTypeDto.type())).thenReturn(Optional.of(new RestaurantType()));

        assertThrows(RestaurantTypeAlreadyExistsException.class, () -> {
            restaurantTypeService.addType(existingTypeDto);
        });

        verify(restaurantTypeRepository, never()).save(any(RestaurantType.class));
    }

    @Test
    void testPatchType() throws RestaurantTypeNotFoundException {
        long typeId = 1L;
        String newType = "New Type";

        RestaurantType existingType = new RestaurantType();
        existingType.setId(typeId);
        existingType.setType("Existing Type");

        when(restaurantTypeRepository.findById(typeId)).thenReturn(Optional.of(existingType));
        when(restaurantTypeRepository.findByType(newType)).thenReturn(Optional.empty());
        when(restaurantTypeRepository.save(existingType)).thenReturn(existingType);

        RestaurantTypeDto restaurantTypeDto = new RestaurantTypeDto(typeId, newType);
        RestaurantTypeDto result = restaurantTypeService.patchType(typeId, restaurantTypeDto);

        assertEquals(newType, result.type());

        verify(restaurantTypeRepository, times(1)).findById(typeId);
        verify(restaurantTypeRepository, times(1)).findByType(newType);
        verify(restaurantTypeRepository, times(1)).save(existingType);
        verifyNoMoreInteractions(restaurantTypeRepository);
    }

    @Test
    public void testDeleteType() throws RestaurantTypeNotFoundException {
        Long id = 1L;
        RestaurantType type = new RestaurantType(); // Assuming Restaurant is your entity class

        when(restaurantTypeRepository.findById(id)).thenReturn(Optional.of(type));
        doNothing().when(restaurantTypeRepository).deleteById(id);

        assertDoesNotThrow(() -> restaurantTypeService.deleteType(id));

        verify(restaurantTypeRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetById() throws RestaurantTypeNotFoundException {

        long restaurantTypeId = 1L;

        RestaurantType existingType = new RestaurantType();
        existingType.setId(restaurantTypeId);
        existingType.setType("ItalianFood");

        when(restaurantTypeRepository.findById(restaurantTypeId)).thenReturn(Optional.of(existingType));

        RestaurantTypeDto result = restaurantTypeService.getById(restaurantTypeId);

        assertEquals(RestaurantTypeConverter.fromModelToRestaurantTypeDto(existingType), result);

        verify(restaurantTypeRepository, times(1)).findById(restaurantTypeId);

        verifyNoMoreInteractions(restaurantTypeRepository);
    }
}