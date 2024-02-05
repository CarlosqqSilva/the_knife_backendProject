package org.mindswap.springtheknife.service.booking;

import org.mindswap.springtheknife.Enum.BookingStatus;
import org.mindswap.springtheknife.converter.BookingConverter;
import org.mindswap.springtheknife.dto.booking.BookingCreateDto;
import org.mindswap.springtheknife.dto.booking.BookingGetDto;
import org.mindswap.springtheknife.dto.booking.BookingPatchDto;
import org.mindswap.springtheknife.exceptions.booking.BookingNotFoundException;
import org.mindswap.springtheknife.exceptions.booking.OperationNotAllowedException;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantNotFoundException;
import org.mindswap.springtheknife.exceptions.user.UserNotFoundException;
import org.mindswap.springtheknife.model.Booking;
import org.mindswap.springtheknife.model.User;
import org.mindswap.springtheknife.repository.BookingRepository;
import org.mindswap.springtheknife.service.restaurant.RestaurantServiceImpl;
import org.mindswap.springtheknife.service.user.UserServiceImpl;
import org.mindswap.springtheknife.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public List<BookingGetDto> getAllBookings(int pageNumber, int pageSize, String sortBy) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sortBy);
        Page<Booking> pageBookings = bookingRepository.findAll(pageRequest);
        return pageBookings.stream()
                .map(BookingConverter::fromModelToBookingDto)
                .toList();
    }

    @Override
    public BookingGetDto getBookingById(Long id) throws BookingNotFoundException {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isEmpty()) {
            throw new BookingNotFoundException(Message.BOOKING_ID + id + Message.NOT_FOUND);
        }
        Booking booking = bookingOptional.get();
        return BookingConverter.fromModelToBookingDto(booking);
    }

    public Booking getBookingId(Long id) throws BookingNotFoundException {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isEmpty()) {
            throw new BookingNotFoundException(Message.BOOKING_ID + id + Message.NOT_EXIST);
        }
        return bookingOptional.get();
    }

    @Override
    public BookingGetDto addBooking(BookingCreateDto booking) throws UserNotFoundException, RestaurantNotFoundException {
        Booking bookingToSave = BookingConverter.fromBookingDtoToModel
                (booking, userServiceImpl.getUserById(booking.userId()),
                        restaurantServiceImpl.getById(booking.restaurantId()));
        bookingRepository.save(bookingToSave);
        return BookingConverter.fromModelToBookingDto(bookingToSave);
    }

    @Override
    public void deleteBooking(Long bookingId) throws BookingNotFoundException {
        bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(Message.BOOKING_ID + bookingId + Message.NOT_FOUND));
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public BookingGetDto patchBooking(Long id, BookingPatchDto booking) throws BookingNotFoundException, OperationNotAllowedException {

        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isEmpty()) {
            throw new BookingNotFoundException(Message.BOOKING_ID + id + Message.NOT_FOUND);
        }
        Booking dbBooking = bookingOptional.get();

        if(dbBooking.getStatus() == BookingStatus.COMPLETE) {
            throw new OperationNotAllowedException(Message.BOOKING_CLOSED);
        }
        if (booking.bookingTime() != null) {
            dbBooking.setBookingTime(booking.bookingTime());
        }
        if (booking.status() != null) {
            dbBooking.setStatus(booking.status());
        }

        return BookingConverter.fromModelToBookingDto(bookingRepository.save(dbBooking));
    }

}



