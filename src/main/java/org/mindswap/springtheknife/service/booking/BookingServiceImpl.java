package org.mindswap.springtheknife.service.booking;

import org.mindswap.springtheknife.converter.BookingConverter;
import org.mindswap.springtheknife.dto.booking.BookingCreateDto;
import org.mindswap.springtheknife.dto.booking.BookingGetDto;
import org.mindswap.springtheknife.dto.booking.BookingPatchDto;
import org.mindswap.springtheknife.exceptions.booking.BookingNotFoundException;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantNotFoundException;
import org.mindswap.springtheknife.exceptions.user.UserNotFoundException;
import org.mindswap.springtheknife.model.Booking;
import org.mindswap.springtheknife.repository.BookingRepository;
import org.mindswap.springtheknife.service.restaurant.RestaurantServiceImpl;
import org.mindswap.springtheknife.service.user.UserServiceImpl;
import org.mindswap.springtheknife.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    private final UserServiceImpl userServiceImpl;

    private final RestaurantServiceImpl restaurantServiceImpl;
    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, UserServiceImpl userServiceImpl, RestaurantServiceImpl restaurantServiceImpl) {
        this.bookingRepository = bookingRepository;
        this.userServiceImpl = userServiceImpl;
        this.restaurantServiceImpl = restaurantServiceImpl;
    }


    @Override
    public List<BookingGetDto> getAllBookings(){
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(BookingConverter::fromModelToBookingDto).toList();
    }

    @Override
    public BookingGetDto getBookingById(Long id) throws BookingNotFoundException {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if(bookingOptional.isEmpty()){
            throw new BookingNotFoundException(Message.BOOKING_ID + id + Message.NOT_FOUND);
        }
        Booking booking = bookingOptional.get();
        return BookingConverter.fromModelToBookingDto(booking);
    }

    @Override
    public BookingGetDto addBooking (BookingCreateDto booking) throws UserNotFoundException, RestaurantNotFoundException {
        Booking bookingToSave = BookingConverter.fromBookingDtoToModel
                (booking, userServiceImpl.getUserById(booking.userId()),
                        restaurantServiceImpl.getById(booking.restaurantId()));
        bookingRepository.save(bookingToSave);
        return BookingConverter.fromModelToBookingDto(bookingToSave);
    }

    @Override
    public void deleteBooking (Long bookingId) throws BookingNotFoundException {
        bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(Message.BOOKING_ID + bookingId + Message.NOT_FOUND));
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public BookingGetDto patchBooking (Long id, BookingPatchDto booking) throws BookingNotFoundException {
        Booking dbBooking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException(Message.BOOKING_ID + id + Message.NOT_FOUND));
        if (bookingRepository.findByBookingTime(booking.bookingTime()).isPresent()) {
            throw new IllegalStateException("booking taken");
        }
        if (booking.status() != null) {
            dbBooking.setStatus(booking.status());
        }
        return BookingConverter.fromModelToBookingDto(bookingRepository.save(dbBooking));
    }
}



