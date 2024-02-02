package org.mindswap.springtheknife.service.restaurantimage;

import org.mindswap.springtheknife.exceptions.restaurant.RestaurantNotFoundException;
import org.mindswap.springtheknife.model.Restaurant;
import org.mindswap.springtheknife.model.RestaurantImage;

import java.io.IOException;

public interface RestaurantImageService {

    RestaurantImage saveRestaurantImage(Restaurant restaurant) throws IOException;
}
