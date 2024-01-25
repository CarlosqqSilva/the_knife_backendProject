package org.mindswap.springtheknife.service.booking;

import org.mindswap.springtheknife.dto.booking.BookingCreateDto;
import org.mindswap.springtheknife.dto.booking.BookingGetDto;
import org.mindswap.springtheknife.dto.booking.BookingPatchDto;
import org.mindswap.springtheknife.exceptions.booking.BookingAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.booking.BookingNotFoundException;

import java.util.List;

public interface BookingService {
    List<BookingGetDto> getBooking();

    BookingGetDto getById(Long id) throws BookingNotFoundException;

    BookingGetDto addBooking(BookingCreateDto booking) throws BookingAlreadyExistsException;

    BookingGetDto patchBooking(Long id, BookingPatchDto booking) throws BookingNotFoundException;

    void deleteBooking(Long id) throws BookingNotFoundException;
}
