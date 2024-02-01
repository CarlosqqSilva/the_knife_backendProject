package org.mindswap.springtheknife.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mindswap.springtheknife.Enum.BookingStatus;
import org.mindswap.springtheknife.converter.BookingConverter;
import org.mindswap.springtheknife.dto.booking.BookingCreateDto;
import org.mindswap.springtheknife.dto.booking.BookingGetDto;
import org.mindswap.springtheknife.dto.booking.BookingPatchDto;
import org.mindswap.springtheknife.exceptions.booking.BookingNotFoundException;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantNotFoundException;
import org.mindswap.springtheknife.exceptions.user.UserNotFoundException;
import org.mindswap.springtheknife.model.Booking;
import org.mindswap.springtheknife.model.City;
import org.mindswap.springtheknife.model.Restaurant;
import org.mindswap.springtheknife.model.User;
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

import java.time.LocalDateTime;
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
    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingConverter bookingConverter;
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

        List<BookingGetDto> result = bookingService.getAllBookings(0,1,"Id");

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

    @Test
    void testAddBooking() throws UserNotFoundException, RestaurantNotFoundException {
        BookingCreateDto bookingCreateDto = new BookingCreateDto(1L, 1L, LocalDateTime.now(), BookingStatus.CONFIRMED);

        User user = new User();
        when(userService.getUserById(1L)).thenReturn(user);

        City city = new City();
        city.setId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.getCity().setId(1L);
        when(restaurantService.getById(1L)).thenReturn(restaurant);

        BookingGetDto result = bookingService.addBooking(bookingCreateDto);

        assertNotNull(result);

        verify(bookingRepository, times(1)).save(any(Booking.class));

        verify(userService, times(1)).getUserById(1L);
        verify(restaurantService, times(1)).getById(1L);

        if (restaurant.getCity() != null) {
            assertNotNull(restaurant.getCity().getId());
        }
    }

    @Test
    void testGetBookingById() throws BookingNotFoundException, UserNotFoundException, RestaurantNotFoundException {
        long bookingId = 1L;

        User user = new User();
        user.setId(1L);
        when(userService.getUserById(1L)).thenReturn(user);

        City city = new City();
        city.setId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.getCity().setId(1L);
        when(restaurantService.getById(1L)).thenReturn(restaurant);

        Booking existingBooking = new Booking();
        existingBooking.setId(bookingId);
        existingBooking.setUser(user);
        existingBooking.setRestaurant(restaurant);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(existingBooking));

        BookingGetDto result = bookingService.getBookingById(bookingId);

        assertEquals(existingBooking.getId(), result.id());
        assertEquals(user.getId(), result.user().userId());

        verify(bookingRepository, times(1)).findById(bookingId);
        verifyNoMoreInteractions(bookingRepository);
    }
    @Test
    void testGetBookingByIdNotFound() {

        long bookingId = 1L;

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        assertThrows(BookingNotFoundException.class, () -> {
            bookingService.getBookingById(bookingId);
        });

        verify(bookingRepository, times(1)).findById(bookingId);
        verifyNoMoreInteractions(bookingRepository);
    }

    @Test
    void testPatchBooking() throws BookingNotFoundException, RestaurantNotFoundException, UserNotFoundException {
        long bookingId = 1L;

        User user = new User();
        user.setId(1L);
        when(userService.getUserById(1L)).thenReturn(user);

        City city = new City();
        city.setId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.getCity().setId(1L);
        when(restaurantService.getById(1L)).thenReturn(restaurant);

        Booking existingBooking = new Booking();
        existingBooking.setId(bookingId);
        existingBooking.setUser(user);
        existingBooking.setRestaurant(restaurant);
        existingBooking.setBookingTime(LocalDateTime.now().minusHours(1));
        existingBooking.setStatus(BookingStatus.CONFIRMED);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(existingBooking));
        when(bookingRepository.findByBookingTime(any())).thenReturn(Optional.empty());

        BookingPatchDto patchDto = new BookingPatchDto(LocalDateTime.now(), BookingStatus.CONFIRMED);

        when(bookingRepository.save(any())).thenReturn(existingBooking);

        BookingGetDto result = bookingService.patchBooking(bookingId, patchDto);

        assertNotNull(result);
        assertEquals(bookingId, result.id());
        assertEquals(BookingStatus.CONFIRMED, result.status());

        verify(bookingRepository, times(1)).findById(bookingId);
        verify(bookingRepository, times(1)).findByBookingTime(patchDto.bookingTime());
        verify(bookingRepository, times(1)).save(existingBooking);
        verifyNoMoreInteractions(bookingRepository);
    }
}