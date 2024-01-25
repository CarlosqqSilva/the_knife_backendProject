package org.mindswap.springtheknife.dto.userexperience;

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

