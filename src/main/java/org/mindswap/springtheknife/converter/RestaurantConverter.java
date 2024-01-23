package org.mindswap.springtheknife.converter;

import org.mindswap.springtheknife.dto.RestaurantGetDto;
import org.mindswap.springtheknife.model.Restaurant;

public class RestaurantConverter {


    public static RestaurantGetDto fromModeltoRestaurantDto(Restaurant restaurant) {
        return new RestaurantGetDto(
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getPhoneNumber(),
                restaurant.getRating()
        );
    }

    public static Restaurant fromRestaurantDtoToModel(RestaurantGetDto restaurantDto) {
        return Restaurant.builder()
              .name(restaurantDto.name())
              .address(restaurantDto.address())
              .phoneNumber(restaurantDto.phoneNumber())
              .rating(restaurantDto.rating())
              .build();
    }
}
