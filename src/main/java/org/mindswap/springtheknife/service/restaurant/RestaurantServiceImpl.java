package org.mindswap.springtheknife.service.restaurant;

import org.mindswap.springtheknife.converter.CityConverter;
import org.mindswap.springtheknife.converter.RestaurantConverter;
import org.mindswap.springtheknife.dto.restaurant.RestaurantGetDto;
import org.mindswap.springtheknife.dto.restaurant.RestaurantPatchDto;
import org.mindswap.springtheknife.dto.restaurant.RestaurantPostDto;
import org.mindswap.springtheknife.exceptions.city.CityNotFoundException;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantNotFoundException;
import org.mindswap.springtheknife.model.City;
import org.mindswap.springtheknife.model.Restaurant;
import org.mindswap.springtheknife.repository.RestaurantRepository;
import org.mindswap.springtheknife.service.city.CityServiceImpl;
import org.mindswap.springtheknife.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository restaurantRepository;

    private final CityServiceImpl cityServiceImpl;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository clientRepository, CityServiceImpl cityServiceImpl) {
        this.restaurantRepository = clientRepository;
        this.cityServiceImpl = cityServiceImpl;
    }

    @Override
    public List<RestaurantGetDto> getRestaurants(){
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(RestaurantConverter::fromModelToRestaurantDto).toList();
    }


    @Override
    public RestaurantGetDto getRestaurant(Long id) throws RestaurantNotFoundException {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isEmpty()) {
            throw new RestaurantNotFoundException(id + Message.USER_ID_DOES_NOT_EXIST);
        }
        return RestaurantConverter.fromModelToRestaurantDto(restaurantOptional.get());

    }

    @Override
    public Restaurant getById(Long id) throws RestaurantNotFoundException {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isEmpty()) {
            throw new RestaurantNotFoundException(id + Message.NOT_EXIST);
        }
        return (restaurantOptional.get());
    }

    @Override
    public RestaurantGetDto addRestaurant(RestaurantPostDto restaurant) throws RestaurantAlreadyExistsException, CityNotFoundException {
        Optional<Restaurant> departmentOptional = this.restaurantRepository.findByEmail(restaurant.email());
        if (departmentOptional.isPresent()) {
            throw new RestaurantAlreadyExistsException("This restaurant already exists.");
        }
        Restaurant departmentCreated = RestaurantConverter.fromRestaurantCreateDtoToEntity(restaurant, cityServiceImpl.getCityById(restaurant.cityId()));
        restaurantRepository.save(departmentCreated);
        return RestaurantConverter.fromModelToRestaurantDto(departmentCreated);


    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws RestaurantNotFoundException {
        restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + restaurantId + " not found."));
        restaurantRepository.deleteById(restaurantId);
    }

    @Override
    public RestaurantGetDto patchRestaurant(Long id, RestaurantPatchDto restaurant) throws RestaurantNotFoundException {
        Restaurant dbRestaurant = restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + id + " not found."));
        if (restaurantRepository.findByEmail(restaurant.email()).isPresent()) {
            throw new IllegalStateException("email taken");
        }
        if (restaurant.address() != null) {
            dbRestaurant.setAddress(restaurant.address());
        }
        if (restaurant.email() != null) {
            dbRestaurant.setEmail(restaurant.email());
        }
        return RestaurantConverter.fromModelToRestaurantDto(restaurantRepository.save(dbRestaurant));
    }
}
