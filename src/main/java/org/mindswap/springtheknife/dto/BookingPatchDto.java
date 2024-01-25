package org.mindswap.springtheknife.dto;

import org.mindswap.springtheknife.Enum.BookingStatus;

import java.time.LocalDateTime;

public record BookingPatchDto(
        LocalDateTime bookingTime,
        BookingStatus status
) {
}
