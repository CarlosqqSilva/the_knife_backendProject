package org.mindswap.springtheknife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public class RestaurantService {

    RestaurantService restaurantService;

    @Autowired
    public RestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/")
    public ResponseEntity<List<RestaurantGetDto>> getRestaurants() {
        return new ResponseEntity<>(restaurantService.getRestaurants(), HttpStatus.OK);
    }
}
