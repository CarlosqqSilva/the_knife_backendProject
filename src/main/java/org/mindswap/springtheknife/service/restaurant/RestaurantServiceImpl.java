package org.mindswap.springtheknife.service.restaurant;

import org.mindswap.springtheknife.converter.RestaurantConverter;
import org.mindswap.springtheknife.dto.restaurant.RestaurantGetDto;
import org.mindswap.springtheknife.dto.restaurant.RestaurantPatchDto;
import org.mindswap.springtheknife.dto.restaurant.RestaurantPostDto;
import org.mindswap.springtheknife.exceptions.city.CityNotFoundException;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantNotFoundException;
import org.mindswap.springtheknife.model.City;
import org.mindswap.springtheknife.model.Restaurant;
import org.mindswap.springtheknife.model.RestaurantImage;
import org.mindswap.springtheknife.model.RestaurantType;
import org.mindswap.springtheknife.repository.RestaurantRepository;
import org.mindswap.springtheknife.repository.RestaurantTypeRepository;
import org.mindswap.springtheknife.service.city.CityServiceImpl;
import org.mindswap.springtheknife.service.restauranttype.RestaurantTypeImpl;
import org.mindswap.springtheknife.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository restaurantRepository;

    private final CityServiceImpl cityServiceImpl;

    private final RestaurantTypeImpl restaurantTypeImpl;
    private final RestaurantTypeRepository restaurantTypeRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository clientRepository, CityServiceImpl cityServiceImpl, RestaurantTypeImpl restaurantTypeImpl, RestaurantTypeRepository restaurantTypeRepository) {
        this.restaurantRepository = clientRepository;
        this.cityServiceImpl = cityServiceImpl;
        this.restaurantTypeImpl = restaurantTypeImpl;
        this.restaurantTypeRepository = restaurantTypeRepository;
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
    public RestaurantGetDto addRestaurant(RestaurantPostDto restaurant) throws RestaurantAlreadyExistsException, CityNotFoundException, IOException {
        List<RestaurantType> restaurantTypes =  restaurant.restaurantTypes().stream().map(restaurantTypeRepository::findById).filter(Optional::isPresent).map(Optional::get).toList();

        Optional<City> cityOptional = Optional.ofNullable(this.cityServiceImpl.getCityById(restaurant.cityId()));
        if (cityOptional.isEmpty()) {
            throw new CityNotFoundException(restaurant.cityId() + Message.CITY_NOT_FOUND);
        }
        Optional<Restaurant> restaurantOpt = this.restaurantRepository.findByEmail(restaurant.email());
        if (restaurantOpt.isPresent()) {
            throw new RestaurantAlreadyExistsException("This restaurant already exists.");
        }

        RestaurantImage newRestaurantImage = new RestaurantImage();
        Restaurant newRestaurant = RestaurantConverter.fromRestaurantCreateDtoToEntity(restaurant, cityServiceImpl.getCityById(restaurant.cityId()),restaurantTypes);
        String prompt = newRestaurant.getRestaurantTypes().getFirst().getType();
        newRestaurant.setImagePath(newRestaurantImage.setImages(prompt + " restaurant facade", newRestaurant.getId(), prompt));
        restaurantRepository.save(newRestaurant);

        return RestaurantConverter.fromModelToRestaurantDto(newRestaurant);

    }

    @Override
    public List<RestaurantGetDto> addListOfRestaurants(List<RestaurantPostDto> restaurantList) throws RestaurantAlreadyExistsException, CityNotFoundException {
        List<RestaurantGetDto> newRestaurantsList = new ArrayList<>();
        for (RestaurantPostDto restaurantPostDto : restaurantList) {
            List<RestaurantType> restaurantTypes =  restaurantPostDto.restaurantTypes().stream().map(restaurantTypeRepository::findById).filter(Optional::isPresent).map(Optional::get).toList();
            Optional<City> cityOptional = Optional.ofNullable(this.cityServiceImpl.getCityById(restaurantPostDto.cityId()));
            if (cityOptional.isEmpty()) {
                throw new CityNotFoundException(restaurantPostDto.cityId() + Message.CITY_NOT_FOUND);
            }
            Optional<Restaurant> restaurantOpt = this.restaurantRepository.findByEmail(restaurantPostDto.email());
            if (restaurantOpt.isPresent()) {
                throw new RestaurantAlreadyExistsException("This restaurant already exists.");
            }
            Restaurant newRestaurant = RestaurantConverter.fromRestaurantCreateDtoToEntity(restaurantPostDto, cityServiceImpl.getCityById(restaurantPostDto.cityId()), restaurantTypes);

            restaurantRepository.save(newRestaurant);
            newRestaurantsList.add(RestaurantConverter.fromModelToRestaurantDto(newRestaurant));
        }

        return newRestaurantsList;
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
