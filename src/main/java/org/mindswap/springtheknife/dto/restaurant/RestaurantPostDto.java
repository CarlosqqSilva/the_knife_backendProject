package org.mindswap.springtheknife.dto.restaurant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.mindswap.springtheknife.dto.city.CityDto;
import org.mindswap.springtheknife.model.RestaurantType;

import java.util.List;
import java.util.Set;

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
        Double longitude,
        Long cityId,
        @NotEmpty
        Set<Long> restaurantTypes
) {

}
