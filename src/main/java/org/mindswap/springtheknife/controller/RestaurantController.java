package org.mindswap.springtheknife.controller;

import jakarta.validation.Valid;
import org.mindswap.springtheknife.dto.RestaurantGetDto;
import org.mindswap.springtheknife.dto.RestaurantPostDto;
import org.mindswap.springtheknife.exceptions.RestaurantAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.RestaurantNotFoundException;
import org.mindswap.springtheknife.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/restaurants")
public class RestaurantController {

    RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/")
    public ResponseEntity<List<RestaurantGetDto>> getRestaurants() {
        return new ResponseEntity<>(restaurantService.getRestaurants(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantGetDto> getRestaurantById(@PathVariable("id") Long id) throws RestaurantNotFoundException {
        return new ResponseEntity<>(restaurantService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<RestaurantGetDto> addRestaurant(@Valid @RequestBody RestaurantPostDto restaurant) throws RestaurantAlreadyExistsException {
        return new ResponseEntity<>(restaurantService.addRestaurant(restaurant), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable("id") Long id) throws RestaurantNotFoundException {
        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>("Restaurant with id " + id + " deleted successfully.", HttpStatus.OK);
    }
}
