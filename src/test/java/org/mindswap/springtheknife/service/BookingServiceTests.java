package org.mindswap.springtheknife.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mindswap.springtheknife.dto.booking.BookingGetDto;
import org.mindswap.springtheknife.exceptions.booking.BookingNotFoundException;
import org.mindswap.springtheknife.model.Booking;
import org.mindswap.springtheknife.repository.BookingRepository;
import org.mindswap.springtheknife.service.booking.BookingService;
import org.mindswap.springtheknife.service.booking.BookingServiceImpl;
import org.mindswap.springtheknife.service.restaurant.RestaurantServiceImpl;
import org.mindswap.springtheknife.service.user.UserServiceImpl;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingServiceTests {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private BookingServiceImpl bookingService;
    @Mock
    private RestaurantServiceImpl restaurantService;
    @Mock
    private UserServiceImpl userService;
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    void deleteData() {
        bookingRepository.deleteAll();
        bookingRepository.resetId();
    }

    @Test
    public void testAllBookings() {
        List<Booking> booking = new ArrayList<>();
        when(bookingRepository.findAll()).thenReturn(booking);

        List<BookingGetDto> result = bookingService.getAllBookings();

        assertEquals(booking, result);
    }

    @Test
    void testDeleteBooking() throws BookingNotFoundException {
        long bookingId = 1L;
        Booking booking = new Booking();

        BookingRepository bookingRepository = mock(BookingRepository.class);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        BookingService bookingService = new BookingServiceImpl(bookingRepository, userService, restaurantService);

        assertDoesNotThrow(() -> bookingService.deleteBooking(bookingId));

        verify(bookingRepository, times(1)).findById(bookingId);
        verify(bookingRepository, times(1)).deleteById(bookingId);
    }

   /* @Test
    @DisplayName("Test create a Booking when Booking returns status code 201")
    public void testCreateBookingReturnCreateAndGetIdEqualsTo1() throws Exception {
        String studentJson = "{\"long\": \"1\",\"booking_time\": \"2024-02-01T14:30:00\", \"status\": \"CONFIRMED\", \"restaurant_id\": \"1\"}";
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bookings/", new Object[0]).contentType(MediaType.APPLICATION_JSON).content(studentJson)).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        String responseContent = result.getResponse().getContentAsString();
        Booking booking = (Booking) objectMapper.readValue(responseContent, Booking.class);
        AssertionsForClassTypes.assertThat(booking.getId()).isEqualTo(1L);
        AssertionsForClassTypes.assertThat(booking.getBookingTime()).isEqualTo("2024-02-01T14:30:00");
        AssertionsForClassTypes.assertThat(booking.getStatus()).isEqualTo("CONFIRMED");
        AssertionsForClassTypes.assertThat(booking.getRestaurant().getId()).isEqualTo("1");
    }*/
}