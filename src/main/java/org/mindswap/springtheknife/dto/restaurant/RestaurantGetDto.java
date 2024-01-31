package org.mindswap.springtheknife.dto.restaurant;


import jakarta.validation.constraints.NotEmpty;
import org.mindswap.springtheknife.dto.restaurantTypeDto.RestaurantTypeDto;
import org.mindswap.springtheknife.model.Address;
import org.mindswap.springtheknife.model.RestaurantType;
import java.util.Set;

public record RestaurantGetDto(

        String cityName,
        String name,
        String email,
        Address address,
        String phoneNumber,
        Double rating,
        @NotEmpty
        Set<RestaurantTypeDto> restaurantTypes

) {
}
