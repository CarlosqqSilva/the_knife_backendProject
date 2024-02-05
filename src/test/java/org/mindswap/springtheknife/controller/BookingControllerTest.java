package org.mindswap.springtheknife.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindswap.springtheknife.Enum.BookingStatus;
import org.mindswap.springtheknife.dto.booking.BookingGetDto;
import org.mindswap.springtheknife.dto.booking.BookingPatchDto;
import org.mindswap.springtheknife.dto.restaurant.RestaurantGetDto;
import org.mindswap.springtheknife.dto.restaurantTypeDto.RestaurantTypeDto;
import org.mindswap.springtheknife.dto.user.UserGetDto;
import org.mindswap.springtheknife.exceptions.booking.BookingNotFoundException;
import org.mindswap.springtheknife.model.Address;
import org.mindswap.springtheknife.repository.BookingRepository;
import org.mindswap.springtheknife.repository.RestaurantRepository;
import org.mindswap.springtheknife.repository.UserRepository;
import org.mindswap.springtheknife.service.booking.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mindswap.springtheknife.Enum.BookingStatus.CONFIRMED;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

    private static ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private Address address;

    @MockBean
    private BookingServiceImpl bookingServiceImpl;

    public BookingControllerTest() {
    }


    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void init() {
        bookingRepository.deleteAll();
        bookingRepository.resetId();
        restaurantRepository.deleteAll();
        restaurantRepository.resetId();
        userRepository.deleteAll();
        userRepository.resetId();
    }

    @Test
    void contextLoads() {

    }

    @Test
    @DisplayName("Test get all Bookings when Booking on database returns empty list")
    void testGetAllBookingsWhenNoBookingsOnDatabaseReturnsEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/bookings/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    void testGetAllBooking() throws Exception {
        BookingStatus status = BookingStatus.CONFIRMED;
        Set<RestaurantTypeDto> restaurantTypeDto = new HashSet<>();

        RestaurantGetDto restaurant = new RestaurantGetDto("porto", "restaurant1", "email@gmail.com",
                address, "+351223456874", 0.0, restaurantTypeDto);
        RestaurantGetDto restaurant1 = new RestaurantGetDto("porto", "restaurant2", "email2@gmail.com",
                address, "+351225556874", 0.0, restaurantTypeDto);
        UserGetDto user = new UserGetDto(1L, "username1", new HashSet<>());
        UserGetDto user1 = new UserGetDto(2L, "username2", new HashSet<>());
        List<BookingGetDto> bookings = new ArrayList<>();

        bookings.add(new BookingGetDto(1L, user, restaurant, LocalDateTime.of(2020, 1, 1, 12, 0), status));
        bookings.add(new BookingGetDto(2L, user1, restaurant1, LocalDateTime.of(2020, 1, 1, 1, 0), status));


        when(bookingServiceImpl.getAllBookings(0, 5, "user")).thenReturn(bookings);


        mockMvc.perform(get("/api/v1/bookings/")
                        .param("pageNumber", "0")
                        .param("pageSize", "5")
                        .param("sortBy", "user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));


        verify(bookingServiceImpl, times(1)).getAllBookings(0, 5, "user");
    }

    @Test
    void testGetBookingById() throws Exception {
        Set<RestaurantTypeDto> restaurantTypeDto = new HashSet<>();

        RestaurantGetDto restaurant = new RestaurantGetDto("porto", "restaurant1", "email@gmail.com",
                address, "+351223456874", 0.0, restaurantTypeDto);

        UserGetDto user = new UserGetDto(1L, "username1", new HashSet<>());
        BookingGetDto booking = new BookingGetDto(1L, user, restaurant, LocalDateTime.now(), CONFIRMED);

        when(bookingServiceImpl.getBookingById(1L)).thenReturn(booking);

        mockMvc.perform(get("/api/v1/bookings/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));


        verify(bookingServiceImpl, times(1)).getBookingById(1L);
    }


    @Test
    @DisplayName("Test create a Booking and return that Booking with status code 201")
    void testCreateBookingReturnCreateAndGetIdEqualsTo1() throws Exception {
        //Given
        String restaurantTypesJson = "{\"type\":\"indian\",\"restaurant\":\"[1]\"}";
        String cityJson = "{\"name\":\"Porto\",\"restaurants\":\"[1]\"}";
        String restaurantJson = "{\"address\":{\"street\":\"rua\",\"number\":\"10\",\"zipCode\":\"4000\"},\"latitude\":\"51.5544\",\"longitude\":\"-0.0876\",\"name\":\"Food\",\"phoneNumber\":\"+351213456789\",\"restaurantTypes\":[1],\"email\":\"f@gmail.com\",\"cityId\":\"1\"}";

        String userJson = "{\"dateOfBirth\":\"2020-01-01\",\"password\":\"156465\",\"firstName\":\"iol\",\"lastName\":\"gfgd\",\"userName\":\"fop\",\"email\":\"ll@gmail.com\",\"favoriteRestaurants\":[1]}";

        String bookingJson = "{\"bookingTime\":\"2020-01-01T00:00\",\"restaurantId\":\"1\",\"userId\":\"1\",\"status\":\"CONFIRMED\"}";


        //When

        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/restaurantTypes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantTypesJson))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cities/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cityJson))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/restaurants/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult result4 = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andReturn();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bookings/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingJson))
                .andExpect(status().isCreated())
                .andReturn();

        //Then
        String responseContent = new String(result.getResponse().getContentAsByteArray());

        BookingGetDto booking = objectMapper.readValue(responseContent, BookingGetDto.class);

        assertThat(booking.id()).isEqualTo(1L);
        assertThat(booking.bookingTime()).isEqualTo(LocalDateTime.of(2020, 1, 1, 0, 0));
        assertThat(booking.status()).isEqualTo(CONFIRMED);
        assertThat(booking.user().userId()).isEqualTo(1L);
        assertThat(booking.restaurant().name()).isEqualTo("Food");
    }


    @Test
    void testDeleteBooking() throws Exception {
        doNothing().when(bookingServiceImpl).deleteBooking(any(Long.class));

        mockMvc.perform(delete("/api/v1/bookings/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookingServiceImpl, times(1)).deleteBooking(1L);
    }

    @Test
    void testPatchBooking() throws BookingNotFoundException {

        Long id = 1L;
        LocalDateTime bookingTime = LocalDateTime.now();
        BookingStatus status = BookingStatus.CONFIRMED;
        BookingPatchDto bookingPatchDto = new BookingPatchDto(bookingTime, status);

        UserGetDto user = new UserGetDto(1L, "username1", new HashSet<>());
        RestaurantGetDto restaurant = new RestaurantGetDto("porto", "restaurant1", "email@gmail.com",
                address, "+351223456874", 0.0, new HashSet<>());
        BookingGetDto expected = new BookingGetDto(id, user, restaurant, bookingTime, status);

        when(bookingServiceImpl.patchBooking(id, bookingPatchDto)).thenReturn(expected).thenReturn(expected);

        BookingController bookingController = new BookingController(bookingServiceImpl);

        ResponseEntity<BookingGetDto> responseEntity = bookingController.patchBooking(id, bookingPatchDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expected, responseEntity.getBody());
        verify(bookingServiceImpl, times(2)).patchBooking(id, bookingPatchDto);
        verifyNoMoreInteractions(bookingServiceImpl);
    }


}


