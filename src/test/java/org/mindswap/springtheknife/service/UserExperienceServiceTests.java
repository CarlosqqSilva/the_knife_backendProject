package org.mindswap.springtheknife.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindswap.springtheknife.converter.UserExperienceConverter;
import org.mindswap.springtheknife.dto.userexperience.UserExperienceCreateDto;
import org.mindswap.springtheknife.dto.userexperience.UserExperienceGetDto;
import org.mindswap.springtheknife.dto.userexperience.UserExperiencePatchDto;
import org.mindswap.springtheknife.exceptions.booking.BookingNotFoundException;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantNotFoundException;
import org.mindswap.springtheknife.exceptions.user.UserNotFoundException;
import org.mindswap.springtheknife.exceptions.userexperience.UserExperienceNotFoundException;
import org.mindswap.springtheknife.model.*;
import org.mindswap.springtheknife.repository.UserExperienceRepository;
import org.mindswap.springtheknife.service.restaurant.RestaurantServiceImpl;
import org.mindswap.springtheknife.service.user.UserServiceImpl;
import org.mindswap.springtheknife.service.userexperience.UserExperienceServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserExperienceServiceTests {

    private static ObjectMapper objectMapper;
    @Mock
    private UserExperienceRepository userExperienceRepository;
    @Mock
    private UserServiceImpl userServiceImpl;
    @Mock
    private RestaurantServiceImpl restaurantServiceImpl;
    @InjectMocks
    private UserExperienceServiceImpl userExperienceService;


    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    void deleteData() {
        userExperienceRepository.deleteAll();
        userExperienceRepository.resetId();
    }

    @Test
    void contextLoads() {

    }

    @Test
    @DisplayName("Test to get all user experiences")
    void testGetAllUsersExperiences() {

        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "id";
        List<UserExperience> userExperiences = IntStream.range(0, pageSize)
                .mapToObj(i -> {
                    UserExperience userExperience = new UserExperience();
                    User user = new User();
                    City city = Mockito.mock(City.class);
                    Mockito.when(city.getName()).thenReturn("Test City");
                    Restaurant restaurant = Mockito.mock(Restaurant.class);
                    Mockito.when(restaurant.getCity()).thenReturn(city);
                    userExperience.setUser(user);
                    userExperience.setRestaurant(restaurant);
                    userExperience.setRating(5.0);
                    return userExperience;
                })
                .collect(Collectors.toList());
        Page<UserExperience> pageUserExperiences = new PageImpl<>(userExperiences);
        when(userExperienceRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sortBy)))
                .thenReturn(pageUserExperiences);

        List<UserExperienceGetDto> result = userExperienceService.getAllUsersExperiences(pageNumber, pageSize, sortBy);

        assertEquals(pageSize, result.size());
    }

    @Test
    @DisplayName("Test to get user experience by id")
    void testGetUserExperienceById() throws UserExperienceNotFoundException {

        Long id = 1L;
        UserExperience userExperience = new UserExperience();
        User user = new User();
        City city = Mockito.mock(City.class);
        Mockito.when(city.getName()).thenReturn("Test City");
        Restaurant restaurant = Mockito.mock(Restaurant.class);
        Mockito.when(restaurant.getCity()).thenReturn(city);
        userExperience.setUser(user);
        userExperience.setRestaurant(restaurant);
        userExperience.setRating(5.0);
        when(userExperienceRepository.findById(id)).thenReturn(Optional.of(userExperience));

    }

    @Test
    @DisplayName("Test to get user experience by id - Not Found")
    void testGetUserExperienceById_NotFound() {

        Long id = 1L;
        when(userExperienceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserExperienceNotFoundException.class, () -> userExperienceService.getUserExperienceById(id));
    }

    @Test
    @DisplayName("Test to add user experience by booking id - User not found")
    void testAddNewUserExperience_UserNotFound() throws UserNotFoundException {

        Long bookingId = 1L;
        Long userId = 1L;
        Long restaurantId = 1L;
        Double rating = 5.0;
        String comment = "Test comment";
        UserExperienceCreateDto userExperienceCreateDto = new UserExperienceCreateDto(bookingId, userId, restaurantId, rating, comment);
        when(userServiceImpl.getUserById(userId)).thenThrow(new UserNotFoundException("User not found"));

        assertThrows(UserNotFoundException.class, () -> userExperienceService.addNewUserExperience(userExperienceCreateDto));
    }

    @Test
    @DisplayName("Test to add user experience by booking id - Restaurant not found")
    void testAddNewUserExperience_RestaurantNotFound() throws UserNotFoundException, RestaurantNotFoundException {

        Long bookingId = 1L;
        Long userId = 1L;
        Long restaurantId = 1L;
        Double rating = 5.0;
        String comment = "Test comment";
        UserExperienceCreateDto userExperienceCreateDto = new UserExperienceCreateDto(bookingId, userId, restaurantId, rating, comment);
        User user = new User();
        when(userServiceImpl.getUserById(userId)).thenReturn(user);
        when(restaurantServiceImpl.getById(restaurantId)).thenThrow(new RestaurantNotFoundException("Restaurant not found"));

        assertThrows(RestaurantNotFoundException.class, () -> userExperienceService.addNewUserExperience(userExperienceCreateDto));
    }

    @Test
    @DisplayName("Test to add user experience by booking id")
    void testAddNewUserExperience() throws UserNotFoundException, RestaurantNotFoundException, UserExperienceNotFoundException, BookingNotFoundException {

        Long bookingId = 1L;
        Long userId = 1L;
        Long restaurantId = 1L;
        Double rating = 5.0;
        String comment = "Test comment";
        UserExperienceCreateDto userExperienceCreateDto = new UserExperienceCreateDto(bookingId, userId, restaurantId, rating, comment);
        Booking booking = new Booking();
        User user = new User();
        Restaurant restaurant = Mockito.mock(Restaurant.class);
        City city = new City();
        city.setName("Test City");
        Mockito.when(restaurant.getCity()).thenReturn(city);
        when(userServiceImpl.getUserById(userId)).thenReturn(user);
        when(restaurantServiceImpl.getById(restaurantId)).thenReturn(restaurant);
        UserExperience userExperience = UserExperienceConverter.fromUserExperienceCreateDtoToEntity(userExperienceCreateDto, booking, user, restaurant);
        userExperience.setTimestamp(LocalDateTime.now());
        when(userExperienceRepository.save(any())).thenReturn(userExperience);

        UserExperienceGetDto result = userExperienceService.addNewUserExperience(userExperienceCreateDto);

        assertEquals(userExperience.getRating(), result.rating());
    }

    @Test
    @DisplayName("Test to delete user experience by booking id - User experience not found")
    void testDeleteUserExperience_UserExperienceNotFound() {

        Long userExperienceId = 1L;
        when(userExperienceRepository.findById(userExperienceId)).thenReturn(Optional.empty());

        assertThrows(UserExperienceNotFoundException.class, () -> userExperienceService.deleteUserExperience(userExperienceId));
    }

    @Test
    @DisplayName("Test to delete user experience")
    void testDeleteUserExperience() throws UserExperienceNotFoundException {

        Long userExperienceId = 1L;
        UserExperience userExperience = new UserExperience();
        when(userExperienceRepository.findById(userExperienceId)).thenReturn(Optional.of(userExperience));

        userExperienceService.deleteUserExperience(userExperienceId);

        verify(userExperienceRepository, times(1)).deleteById(userExperienceId);
    }

    @Test
    @DisplayName("Test to update user experience - User experience not found")
    void testUpdateUserExperience_UserExperienceNotFound() {

        Long userExperienceId = 1L;
        UserExperiencePatchDto userExperiencePatchDto = new UserExperiencePatchDto(4.0, "Updated comment");
        when(userExperienceRepository.findById(userExperienceId)).thenReturn(Optional.empty());


        assertThrows(UserExperienceNotFoundException.class, () -> userExperienceService.updateUserExperience(userExperienceId, userExperiencePatchDto));
    }

    @Test
    @DisplayName("Test to update user experience")
    void testUpdateUserExperience() throws UserExperienceNotFoundException {

        Long userExperienceId = 1L;
        UserExperiencePatchDto userExperiencePatchDto = new UserExperiencePatchDto(4.0, "Updated comment");
        UserExperience userExperience = new UserExperience();
        userExperience.setRating(3.0);
        userExperience.setComment("Original comment");
        when(userExperienceRepository.findById(userExperienceId)).thenReturn(Optional.of(userExperience));
        when(userExperienceRepository.save(any(UserExperience.class))).thenAnswer(i -> i.getArguments()[0]);

        UserExperiencePatchDto result = userExperienceService.updateUserExperience(userExperienceId, userExperiencePatchDto);

    }

}