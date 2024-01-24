package org.mindswap.springtheknife.dto;

public record RestaurantGetDto(
        String name,
        String address,
        String phoneNumber,
        Double rating
) {
}
