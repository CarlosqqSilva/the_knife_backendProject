package org.mindswap.springtheknife.dto.booking;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.mindswap.springtheknife.Enum.BookingStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public record BookingPatchDto(

        LocalDateTime bookingTime,

        @Enumerated(EnumType.STRING)
        BookingStatus status
) implements Serializable {
}
