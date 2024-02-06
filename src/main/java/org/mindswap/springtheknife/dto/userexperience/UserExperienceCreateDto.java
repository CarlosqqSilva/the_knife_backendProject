package org.mindswap.springtheknife.dto.userexperience;

import jakarta.validation.constraints.*;
import org.mindswap.springtheknife.utils.Message;


public record UserExperienceCreateDto(

        @NotNull(message = Message.BOOKING_ID_MANDATORY)
        Long bookingId,
        @NotNull(message = Message.USER_ID_MANDATORY)
        Long userId,
        @NotNull(message = Message.RESTAURANT_ID_MANDATORY)
        Long restaurantId,

        @NotNull(message = Message.RATING_MANDATORY)
        @Min(value = 0, message = Message.INVALID_RATING)
        @Max(value = 10, message = Message.INVALID_RATING)
        Double rating,
        @NotBlank(message = Message.COMMENT_MANDATORY)
        @Pattern(regexp = "^[^\\n]{1,250}$", message = Message.INVALID_COMMENT)
        String comment

)
{

}

