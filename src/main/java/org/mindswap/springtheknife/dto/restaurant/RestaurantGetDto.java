package org.mindswap.springtheknife.dto.restaurant;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.mindswap.springtheknife.dto.restaurantTypeDto.RestaurantTypeDto;
import org.mindswap.springtheknife.model.Address;
import org.mindswap.springtheknife.model.RestaurantType;
import org.mindswap.springtheknife.utils.Message;

import java.io.Serializable;
import java.util.Set;

import static org.mindswap.springtheknife.utils.Message.*;

public record RestaurantGetDto(

        String cityName,
        @NotBlank(message = RESTAURANT_NAME_MANDATORY)
        @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+){0,19}$", message = VALID_RESTAURANT_NAME)
        String name,
        @Email(message = EMAIL_MANDATORY)
        @Pattern(regexp = EMAIL_VALIDATOR, message =VALID_EMAIL)
        String email,
        Address address,
        @NotBlank(message = PHONE_NUMBER_MANDATORY)
        @Pattern(regexp = PHONE_NUMBER_VALIDATOR, message = INVALID_PHONE_NUMBER)
        String phoneNumber,

        Double rating,
        @NotEmpty
        Set<RestaurantTypeDto> restaurantTypes
        
) implements Serializable {

}
