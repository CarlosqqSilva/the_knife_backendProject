package org.mindswap.springtheknife.service;


import org.mindswap.springtheknife.converter.BookingConverter;
import org.mindswap.springtheknife.dto.*;
import org.mindswap.springtheknife.exceptions.BookingAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.BookingNotFoundException;
import org.mindswap.springtheknife.model.Booking;
import org.mindswap.springtheknife.repository.BookingRepository;
import org.mindswap.springtheknife.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<BookingGetDto> getBooking(){
        List<Booking> booking = bookingRepository.findAll();
        return booking.stream().map(BookingConverter::fromModelToBookingDto).toList();
    }

    @Override
    public BookingGetDto getById(Long id) throws BookingNotFoundException {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException(Message.BOOKING_ID + id + Message.NOT_FOUND));
        return BookingConverter.fromModelToBookingDto(booking);
    }

    @Override
    public BookingGetDto addBooking (BookingCreateDto booking) throws BookingAlreadyExistsException {
        Optional<Booking> bookingOptional = bookingRepository.findByBookingTime (booking.bookingTime());
        if (bookingOptional.isPresent()) {
            throw new BookingAlreadyExistsException(Message.ALREADY_EXISTS);
        }
        Booking booking1 = BookingConverter.fromBookingDtoToModel(booking);
        return BookingConverter.fromModelToBookingDto(bookingRepository.save(booking1));
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