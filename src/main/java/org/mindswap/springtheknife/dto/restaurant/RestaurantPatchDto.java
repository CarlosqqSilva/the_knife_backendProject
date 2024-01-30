package org.mindswap.springtheknife.dto.restaurant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.mindswap.springtheknife.model.Address;

public record RestaurantPatchDto(
        Address address,
        @Email
        String email


) {
}
