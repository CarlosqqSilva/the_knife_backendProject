package org.mindswap.springtheknife.controller;

import org.mindswap.springtheknife.dto.RestaurantGetDto;
import org.mindswap.springtheknife.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<RestaurantGetDto> getRestaurantById(@PathVariable("id") Long id) throws NonExistenModelException {
        return new ResponseEntity<>(restaurantService.getById(id), HttpStatus.OK);
    }
}
