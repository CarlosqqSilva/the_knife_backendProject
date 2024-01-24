package org.mindswap.springtheknife.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RestaurantPostDto(
        @NotBlank(message = "Restaurant must have a name.")
        String name,
        @NotBlank(message = "Restaurant must have an address.")
        String address,
        @Email(message = "Restaurant must have an email address.")
        String email,
        @NotBlank(message = "Restaurant must have a phone number.")
        String phoneNumber,
        Double latitude,
        Double longitude
) {
}
