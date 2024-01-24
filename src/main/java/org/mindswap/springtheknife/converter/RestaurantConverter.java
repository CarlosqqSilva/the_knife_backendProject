package org.mindswap.springtheknife.converter;

import org.mindswap.springtheknife.dto.RestaurantGetDto;
import org.mindswap.springtheknife.dto.RestaurantPostDto;
import org.mindswap.springtheknife.model.Restaurant;

public class RestaurantConverter {


    public static RestaurantGetDto fromModeltoRestaurantDto(Restaurant restaurant) {
        return new RestaurantGetDto(
                restaurant.getName(),
                restaurant.getEmail(),
                restaurant.getAddress(),
                restaurant.getPhoneNumber(),
                restaurant.getRating()
        );
    }

    public static Restaurant fromRestaurantDtoToModel(RestaurantPostDto restaurantDto) {
        return Restaurant.builder()
                .name(restaurantDto.name())
                .address(restaurantDto.address())
                .email(restaurantDto.email())
                .phoneNumber(restaurantDto.phoneNumber())
                .latitude(restaurantDto.latitude())
                .longitude(restaurantDto.longitude())
                .build();
    }
}
