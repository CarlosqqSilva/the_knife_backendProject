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
    public ResponseEntity<List<RestaurantGetDto>> getRestaurants(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false) String sortBy
    ) {
        return new ResponseEntity<>(restaurantServiceImpl.getAllRestaurants(pageNumber, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantGetDto> getRestaurantById(@PathVariable("id") Long id) throws RestaurantNotFoundException {
        return new ResponseEntity<>(restaurantServiceImpl.getRestaurant(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<RestaurantGetDto> addRestaurant(@Valid @RequestBody RestaurantPostDto restaurant) throws RestaurantAlreadyExistsException, CityNotFoundException {
        return new ResponseEntity<>(restaurantServiceImpl.addRestaurant(restaurant), HttpStatus.OK);
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
}
