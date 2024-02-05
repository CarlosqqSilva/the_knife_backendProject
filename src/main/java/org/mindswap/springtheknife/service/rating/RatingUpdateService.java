package org.mindswap.springtheknife.service.rating;

import org.mindswap.springtheknife.model.Restaurant;
import org.mindswap.springtheknife.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingUpdateService {
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RatingUpdateService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Scheduled(fixedRate = 10000) // runs every hour (todo change to 3600000 later on)
    public void updateRestaurantRatings() {
        List<Restaurant> allRestaurants = restaurantRepository.findAll();
        for (Restaurant restaurant : allRestaurants) {
            Double averageRating = restaurantRepository.findAverageRating(restaurant.getId());
            if (averageRating == null) {
                averageRating = 0.0;
            }
            restaurant.setRating(averageRating);
            restaurantRepository.save(restaurant);
        }
    }
}