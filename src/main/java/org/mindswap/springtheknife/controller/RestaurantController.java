package org.mindswap.springtheknife.controller;

import jakarta.validation.Valid;
import org.mindswap.springtheknife.dto.restaurant.RestaurantGetDto;
import org.mindswap.springtheknife.dto.restaurant.RestaurantPatchDto;
import org.mindswap.springtheknife.dto.restaurant.RestaurantPostDto;
import org.mindswap.springtheknife.exceptions.city.CityNotFoundException;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantNotFoundException;
import org.mindswap.springtheknife.service.restaurant.RestaurantService;
import org.mindswap.springtheknife.service.restaurant.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/restaurants")
public class RestaurantController {

   private final RestaurantServiceImpl restaurantServiceImpl;

    @Autowired
    public RestaurantController(RestaurantServiceImpl restaurantServiceImpl) {
        this.restaurantServiceImpl = restaurantServiceImpl;

    }

    @GetMapping("/")
    public ResponseEntity<List<RestaurantGetDto>> getRestaurants() {
        return new ResponseEntity<>(restaurantServiceImpl.getRestaurants(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantGetDto> getRestaurantById(@PathVariable("id") Long id) throws RestaurantNotFoundException {
        return new ResponseEntity<>(restaurantServiceImpl.getRestaurant(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<RestaurantGetDto> addRestaurant(@Valid @RequestBody RestaurantPostDto restaurant) throws RestaurantAlreadyExistsException, CityNotFoundException, IOException, RestaurantNotFoundException {
        return new ResponseEntity<>(restaurantServiceImpl.addRestaurant(restaurant), HttpStatus.OK);
    }
    @PostMapping("/list/")
    public ResponseEntity<List<RestaurantGetDto>> addRestaurant(@Valid @RequestBody List<RestaurantPostDto> restaurantList) throws RestaurantAlreadyExistsException, CityNotFoundException, IOException {
        return new ResponseEntity<>(restaurantServiceImpl.addListOfRestaurants(restaurantList), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantGetDto> patchRestaurant(@Valid @PathVariable("id") Long id, @Valid @RequestBody RestaurantPatchDto restaurant) throws RestaurantNotFoundException {
        return new ResponseEntity<>(restaurantServiceImpl.patchRestaurant(id, restaurant), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable("id") Long id) throws RestaurantNotFoundException {
        restaurantServiceImpl.deleteRestaurant(id);
        return new ResponseEntity<>("Restaurant with id " + id + " deleted successfully.", HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<RestaurantGetDto> addRestaurantWithImage(@Valid @RequestBody RestaurantPostDto restaurant) throws RestaurantAlreadyExistsException, CityNotFoundException, IOException {
        return new ResponseEntity<>(restaurantServiceImpl.addRestaurantWithImage(restaurant), HttpStatus.OK);
    }

    @PostMapping("/list/generate")
    public ResponseEntity<List<RestaurantGetDto>> addRestaurantListWithImage(@Valid @RequestBody List<RestaurantPostDto> restaurantList) throws RestaurantAlreadyExistsException, CityNotFoundException, IOException {
        return new ResponseEntity<>(restaurantServiceImpl.addListOfRestaurantsWithImage(restaurantList), HttpStatus.OK);
    }
}
