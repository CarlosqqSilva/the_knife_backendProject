package org.mindswap.springtheknife.converter;
import org.mindswap.springtheknife.dto.booking.BookingCreateDto;
import org.mindswap.springtheknife.dto.booking.BookingGetDto;
import org.mindswap.springtheknife.model.Booking;

public class BookingConverter {
    public static BookingGetDto fromModelToBookingDto(Booking booking) {
        return new BookingGetDto(
                booking.getBookingTime(),
                booking.getStatus()
        );
    }

    public static Booking fromBookingDtoToModel(BookingCreateDto bookingCreateDto) {
        return Booking.builder()
                .bookingTime(bookingCreateDto.bookingTime())
                .status(bookingCreateDto.status())
                .build();
    }
}
