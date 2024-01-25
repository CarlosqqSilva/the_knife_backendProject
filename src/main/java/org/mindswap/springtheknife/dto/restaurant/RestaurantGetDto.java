package org.mindswap.springtheknife.dto.restaurant;

public record RestaurantGetDto(
        String name,
        String email,
        String address,
        String phoneNumber,
        Double rating
) {
}
