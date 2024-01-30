package org.mindswap.springtheknife.dto.restaurant;

public record RestaurantGetDto(

        Long cityId,
        String name,
        String email,
        String address,
        String phoneNumber,
        Double rating

) {
}
