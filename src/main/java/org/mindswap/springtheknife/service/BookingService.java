package org.mindswap.springtheknife.service;

import org.mindswap.springtheknife.dto.BookingCreateDto;
import org.mindswap.springtheknife.dto.BookingGetDto;
import org.mindswap.springtheknife.dto.BookingPatchDto;
import org.mindswap.springtheknife.exceptions.BookingAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.BookingNotFoundException;

import java.util.List;

public interface BookingService {
    List<BookingGetDto> getBooking();

    BookingGetDto getById(Long id) throws BookingNotFoundException;

    BookingGetDto addBooking(BookingCreateDto booking) throws BookingAlreadyExistsException;

    BookingGetDto patchBooking(Long id, BookingPatchDto booking) throws BookingNotFoundException;

    void deleteBooking(Long id) throws BookingNotFoundException;
}
