package org.mindswap.springtheknife.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mindswap.springtheknife.converter.CityConverter;
import org.mindswap.springtheknife.dto.city.CityDto;
import org.mindswap.springtheknife.exceptions.city.CityNotFoundException;
import org.mindswap.springtheknife.exceptions.city.DuplicateCityException;
import org.mindswap.springtheknife.model.City;
import org.mindswap.springtheknife.repository.CityRepository;
import org.mindswap.springtheknife.service.city.CityServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class CityServiceTests {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImpl cityService;

    static MockedStatic<CityConverter> mockedConverter = Mockito.mockStatic(CityConverter.class);

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    void deleteData() {
        cityRepository.deleteAll();
        cityRepository.resetId();
    }

    @Test
    public void testGetCities() {
        List<City> cities = new ArrayList<>();
        when(cityRepository.findAll()).thenReturn(cities);

        List<City> result = cityService.getCities();

        assertEquals(cities, result);
    }

    @Test
    public void testDelete() throws CityNotFoundException {
        long cityId = 1L;

        when(cityRepository.existsById(cityId)).thenReturn(true);

        cityService.delete(cityId);

        verify(cityRepository, times(1)).deleteById(cityId);
    }

    @Test
    void testCreateCityAndConverter() throws DuplicateCityException {
        CityDto cityDto = new CityDto("Porto");
        City city = new City();
        Mockito.when(this.cityRepository.findByName(cityDto.name())).thenReturn(Optional.empty());
        Mockito.when(this.cityRepository.save((City)Mockito.any())).thenReturn(city);
        mockedConverter.when(() -> {
            CityConverter.fromCreateDtoToModel(cityDto);
        }).thenReturn(city);
        this.cityService.create(cityDto);
        ((CityRepository)Mockito.verify(this.cityRepository, Mockito.times(1))).findByName(cityDto.name());
        ((CityRepository)Mockito.verify(this.cityRepository, Mockito.times(1))).save(city);
        Mockito.verifyNoMoreInteractions(new Object[]{this.cityRepository});
        mockedConverter.verify(() -> {
            CityConverter.fromCreateDtoToModel(cityDto);
        });
        mockedConverter.verifyNoMoreInteractions();
        Assertions.assertEquals(city, this.cityService.create(cityDto));
    }

    @Test
    void createCityWithDuplicatedNameThrowsException() {
        CityDto cityDto = new CityDto("Porto");
        Mockito.when(this.cityRepository.findByName(cityDto.name())).thenReturn(Optional.of(new City()));
        assertThrows(DuplicateCityException.class, () -> {
            this.cityService.create(cityDto);
        });
        Assertions.assertEquals("City with name Porto already exists", ((DuplicateCityException) assertThrows(DuplicateCityException.class, () -> {
            this.cityService.create(cityDto);
        })).getMessage());
    }

    @Test
    void testUpdateCity() throws CityNotFoundException {
        long cityId = 1L;
        City existingCity = new City();
        City updatedCity = new City();

        when(cityRepository.findById(cityId)).thenReturn(Optional.of(existingCity));
        when(cityRepository.save(existingCity)).thenReturn(updatedCity);

        cityService.update(cityId, updatedCity);

        assertEquals(updatedCity.getName(), existingCity.getName());

        verify(cityRepository, times(1)).findById(cityId);
        verify(cityRepository, times(1)).save(existingCity);
        verifyNoMoreInteractions(cityRepository);
    }

    @Test
    void testGetCityById() throws CityNotFoundException {
        long cityId = 1L;
        City existingCity = new City();

        when(cityRepository.findById(cityId)).thenReturn(Optional.of(existingCity));

        City result = cityService.get(cityId);

        assertEquals(existingCity, result);

        verify(cityRepository, times(1)).findById(cityId);

        verifyNoMoreInteractions(cityRepository);
    }

    @Test
    void testGetCityByIdNotFoundThrowsException() {
        long nonExistingCityId = 2L;

        when(cityRepository.findById(nonExistingCityId)).thenReturn(Optional.empty());

        assertThrows(CityNotFoundException.class, () -> {
            cityService.get(nonExistingCityId);
        });

        verify(cityRepository, times(1)).findById(nonExistingCityId);
        verifyNoMoreInteractions(cityRepository);
    }
}