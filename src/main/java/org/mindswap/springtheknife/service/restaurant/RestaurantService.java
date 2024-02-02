package org.mindswap.springtheknife.service.restaurant;

import org.mindswap.springtheknife.dto.restaurant.RestaurantGetDto;
import org.mindswap.springtheknife.dto.restaurant.RestaurantPatchDto;
import org.mindswap.springtheknife.dto.restaurant.RestaurantPostDto;
import org.mindswap.springtheknife.dto.user.UserGetDto;
import org.mindswap.springtheknife.exceptions.city.CityNotFoundException;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantNotFoundException;
import org.mindswap.springtheknife.exceptions.user.UserNotFoundException;
import org.mindswap.springtheknife.model.Restaurant;

import java.io.IOException;
import java.util.List;

public interface RestaurantService {

    List<RestaurantGetDto> getRestaurants();

    RestaurantGetDto getRestaurant(Long id) throws RestaurantNotFoundException;

    Restaurant getById(Long id) throws RestaurantNotFoundException;

    RestaurantGetDto addRestaurant(RestaurantPostDto restaurant) throws RestaurantAlreadyExistsException, CityNotFoundException, IOException;
    List<RestaurantGetDto> addListOfRestaurants(List<RestaurantPostDto> restaurantList) throws RestaurantAlreadyExistsException, CityNotFoundException, IOException;

    void deleteRestaurant(Long restaurantId) throws RestaurantNotFoundException;

    RestaurantGetDto patchRestaurant(Long id, RestaurantPatchDto restaurant) throws RestaurantNotFoundException;
}
