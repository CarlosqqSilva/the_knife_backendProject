package org.mindswap.springtheknife.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.mindswap.springtheknife.dto.restaurant.RestaurantGetDto;
import org.mindswap.springtheknife.model.Restaurant;
import org.mindswap.springtheknife.utils.Message;

import java.util.Set;

public record UserGetDto(

    Long userId,
        @NotBlank(message = Message.USERNAME_MANDATORY)
        @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+){0,19}$", message = Message.VALID_USERNAME)
    String userName,
    Set<RestaurantGetDto> favoriteRestaurants
    ){

}
