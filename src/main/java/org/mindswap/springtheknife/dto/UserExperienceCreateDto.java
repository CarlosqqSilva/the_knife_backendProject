package org.mindswap.springtheknife.dto;

import org.mindswap.springtheknife.model.Restaurant;
import org.mindswap.springtheknife.model.User;

import java.time.LocalDateTime;

public record UserExperienceCreateDto(
        Long userId,

        Long restaurantId,

        int rating,

        String comment,

        LocalDateTime timestamp
)
{

}

