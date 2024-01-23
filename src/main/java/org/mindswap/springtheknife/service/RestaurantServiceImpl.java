package org.mindswap.springtheknife.service;

import org.mindswap.springtheknife.converter.RestaurantConverter;
import org.mindswap.springtheknife.dto.RestaurantGetDto;
import org.mindswap.springtheknife.model.Restaurant;
import org.mindswap.springtheknife.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService{

   /* @Override
    public Restaurant addRestaurant(RestaurantPostDto restaurant) {
        return null;
    }*/

    RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository clientRepository) {
        this.restaurantRepository = clientRepository;
    }

    @Override
    public List<RestaurantGetDto> getRestaurants(){
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(RestaurantConverter::fromModeltoRestaurantDto).toList();
    }

    @Override
    public RestaurantGetDto getById(Long id) {
        return null;
    }
}
