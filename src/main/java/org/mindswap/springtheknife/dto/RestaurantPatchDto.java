package org.mindswap.springtheknife.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RestaurantPatchDto(
        String address,
        @Email
        String email
) {
}
