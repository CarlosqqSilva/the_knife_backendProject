package org.mindswap.springtheknife.dto.userexperience;

import org.mindswap.springtheknife.dto.restaurant.RestaurantGetDto;
import org.mindswap.springtheknife.dto.user.UserGetDto;

import java.time.LocalDateTime;

public record UserExperienceGetDto(

        Long id,

        UserGetDto user,

        RestaurantGetDto restaurant,

        int rating,

        String comment,

        LocalDateTime timestamp

) {


}
