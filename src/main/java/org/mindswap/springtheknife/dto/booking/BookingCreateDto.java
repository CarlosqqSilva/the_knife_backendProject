package org.mindswap.springtheknife.dto.booking;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.mindswap.springtheknife.Enum.BookingStatus;
import org.mindswap.springtheknife.utils.Message;

import java.io.Serializable;
import java.time.LocalDateTime;

public record BookingCreateDto(

        Long userId,
        Long restaurantId,
        @NotNull(message = Message.BOOKING_MANDATORY)
        LocalDateTime bookingTime,
        @Enumerated(EnumType.STRING)
        BookingStatus status

) implements Serializable {
}
