package org.mindswap.springtheknife.service;

import org.mindswap.springtheknife.dto.RestaurantGetDto;
import org.mindswap.springtheknife.dto.RestaurantPatchDto;
import org.mindswap.springtheknife.dto.RestaurantPostDto;
import org.mindswap.springtheknife.exceptions.RestaurantAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.RestaurantNotFoundException;

import java.util.List;

public interface RestaurantService {

    List<RestaurantGetDto> getRestaurants();

    RestaurantGetDto getById(Long id) throws RestaurantNotFoundException;

    RestaurantGetDto addRestaurant(RestaurantPostDto restaurant) throws RestaurantAlreadyExistsException;

    void deleteRestaurant(Long restaurantId) throws RestaurantNotFoundException;

    RestaurantGetDto patchRestaurant(Long id, RestaurantPatchDto restaurant) throws RestaurantNotFoundException;
}
