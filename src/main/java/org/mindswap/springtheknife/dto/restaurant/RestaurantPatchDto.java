package org.mindswap.springtheknife.dto.restaurant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static org.mindswap.springtheknife.utils.Message.*;
import static org.mindswap.springtheknife.utils.Message.VALID_EMAIL;

public record RestaurantPatchDto(

        @NotBlank(message = RESTAURANT_ADDRESS_MANDATORY)
        String address,

        @Email(message = EMAIL_MANDATORY)
        @Pattern(regexp = EMAIL_VALIDATOR, message =VALID_EMAIL)
        String email


) {
}
