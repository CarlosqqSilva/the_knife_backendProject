package org.mindswap.springtheknife.dto;

import jakarta.validation.constraints.NotBlank;

public record RestaurantPostDto(
        @NotBlank(message = "Restaurant must have a name.")
        String name,
        @NotBlank(message = "Restaurant must have a address.")
        String address,
        @NotBlank(message = "Restaurant must have a phone number.")
        String phoneNumber,
        Double latitude,
        Double longitude,
        Double rating
) {
}
