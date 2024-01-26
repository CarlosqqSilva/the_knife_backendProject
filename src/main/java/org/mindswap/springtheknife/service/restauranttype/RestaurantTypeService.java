package org.mindswap.springtheknife.service.restauranttype;

import org.mindswap.springtheknife.dto.restaurantTypeDto.RestaurantTypeDto;
import org.mindswap.springtheknife.exceptions.restaurantType.RestaurantTypeAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.restaurantType.RestaurantTypeNotFoundException;

import java.util.List;

public interface RestaurantTypeService {

    List<RestaurantTypeDto> getRestaurantType();

    RestaurantTypeDto getById(Long id) throws RestaurantTypeNotFoundException;

    RestaurantTypeDto addType (RestaurantTypeDto restaurantType) throws RestaurantTypeAlreadyExistsException;

    void deleteType (Long restaurantTypeId) throws RestaurantTypeNotFoundException;

    RestaurantTypeDto patchType (Long id, RestaurantTypeDto restaurantType) throws RestaurantTypeNotFoundException;

}
