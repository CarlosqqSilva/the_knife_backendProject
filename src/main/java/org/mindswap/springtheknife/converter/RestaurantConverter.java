package org.mindswap.springtheknife.converter;

import org.mindswap.springtheknife.dto.restaurant.RestaurantGetDto;
import org.mindswap.springtheknife.dto.restaurant.RestaurantPostDto;
import org.mindswap.springtheknife.model.City;
import org.mindswap.springtheknife.model.Restaurant;

public class RestaurantConverter {


    public static RestaurantGetDto fromModelToRestaurantDto(Restaurant restaurant) {
        return new RestaurantGetDto(
                restaurant.getCity().getCityId(),
                restaurant.getName(),
                restaurant.getEmail(),
                restaurant.getAddress(),
                restaurant.getPhoneNumber(),
                restaurant.getRating()
        );
    }

    public static Restaurant fromRestaurantDtoToModel(RestaurantPostDto restaurantDto, City city) {
        return Restaurant.builder()
                .city(city)
                .name(restaurantDto.name())
                .address(restaurantDto.address())
                .email(restaurantDto.email())
                .phoneNumber(restaurantDto.phoneNumber())
                .latitude(restaurantDto.latitude())
                .longitude(restaurantDto.longitude())
                .build();
    }

    public static Restaurant fromRestaurantDtoToModel(RestaurantGetDto byId) {
        return Restaurant.builder()
                .name(byId.name())
                .address(byId.address())
                .email(byId.email())
                .phoneNumber(byId.phoneNumber())
                .build();

    }

    public static Restaurant fromRestaurantCreateDtoToEntity(RestaurantPostDto restaurant, City city) {
        return Restaurant.builder()
                .city(city)
                .name(restaurant.name())
                .address(restaurant.address())
                .email(restaurant.email())
                .phoneNumber(restaurant.phoneNumber())
                .latitude(restaurant.latitude())
                .longitude(restaurant.longitude())
                .build();
    }
}
