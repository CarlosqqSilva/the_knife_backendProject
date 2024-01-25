package org.mindswap.springtheknife.dto;

import org.mindswap.springtheknife.model.Restaurant;
import org.mindswap.springtheknife.model.User;

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

