package org.mindswap.springtheknife.service;

import org.mindswap.springtheknife.dto.RestaurantGetDto;
import org.mindswap.springtheknife.model.Restaurant;

import java.util.List;

public interface RestaurantService {

    List<RestaurantGetDto> getRestaurants();

    RestaurantGetDto getById(Long id);
}
