package org.mindswap.springtheknife.service.restaurantimage;

import org.mindswap.springtheknife.exceptions.restaurant.RestaurantNotFoundException;
import org.mindswap.springtheknife.model.Restaurant;
import org.mindswap.springtheknife.model.RestaurantImage;
import org.mindswap.springtheknife.repository.RestaurantImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RestaurantImageServiceImpl implements RestaurantImageService {

    private final RestaurantImageRepository restaurantImageRepository;

    @Autowired
    public RestaurantImageServiceImpl(RestaurantImageRepository restaurantImageRepository) {
        this.restaurantImageRepository = restaurantImageRepository;
    }

    @Override
    public RestaurantImage saveRestaurantImage(Restaurant restaurant) throws IOException {
        String prompt = restaurant.getRestaurantTypes().getFirst().getType() + "restaurant facade";
        RestaurantImage restaurantImage = new RestaurantImage();
        restaurantImage.setRestaurant(restaurant);
        restaurantImage.setImages(prompt);
        restaurantImage.setImagePath(restaurantImage.createImageFile(restaurant.getId()));
        return restaurantImageRepository.save(restaurantImage);
    }
}
