package org.mindswap.springtheknife.service;

import org.mindswap.springtheknife.converter.RestaurantConverter;
import org.mindswap.springtheknife.dto.RestaurantGetDto;
import org.mindswap.springtheknife.dto.RestaurantPostDto;
import org.mindswap.springtheknife.exceptions.RestaurantAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.RestaurantNotFoundException;
import org.mindswap.springtheknife.model.Restaurant;
import org.mindswap.springtheknife.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{

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
    public RestaurantGetDto getById(Long id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + id + " not found."));
        return RestaurantConverter.fromModeltoRestaurantDto(restaurant);
    }

    @Override
    public RestaurantGetDto addRestaurant(RestaurantPostDto restaurant) throws RestaurantAlreadyExistsException {
        Optional<Restaurant> departmentOptional = restaurantRepository.findByEmail(restaurant.email());
        if (departmentOptional.isPresent()) {
            throw new RestaurantAlreadyExistsException("This restaurant already exists.");
        }
        Restaurant departmentCreated = RestaurantConverter.fromRestaurantDtoToModel(restaurant);
        return RestaurantConverter.fromModeltoRestaurantDto(restaurantRepository.save(departmentCreated));
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws RestaurantNotFoundException {
        restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + restaurantId + " not found."));
        restaurantRepository.deleteById(restaurantId);
    }
}
