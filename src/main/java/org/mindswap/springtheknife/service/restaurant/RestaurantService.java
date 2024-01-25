package org.mindswap.springtheknife.service.restaurant;

import org.mindswap.springtheknife.dto.restaurant.RestaurantGetDto;
import org.mindswap.springtheknife.dto.restaurant.RestaurantPatchDto;
import org.mindswap.springtheknife.dto.restaurant.RestaurantPostDto;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantNotFoundException;

import java.util.List;

public interface RestaurantService {

    List<RestaurantGetDto> getRestaurants();

    RestaurantGetDto getById(Long id) throws RestaurantNotFoundException;

    RestaurantGetDto addRestaurant(RestaurantPostDto restaurant) throws RestaurantAlreadyExistsException;

    void deleteRestaurant(Long restaurantId) throws RestaurantNotFoundException;

    RestaurantGetDto patchRestaurant(Long id, RestaurantPatchDto restaurant) throws RestaurantNotFoundException;
}
