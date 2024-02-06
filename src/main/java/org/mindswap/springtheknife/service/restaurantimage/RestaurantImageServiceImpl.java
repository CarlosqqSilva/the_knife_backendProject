package org.mindswap.springtheknife.service.restaurantimage;

import org.mindswap.springtheknife.model.Restaurant;
import org.mindswap.springtheknife.model.RestaurantImage;
import org.mindswap.springtheknife.repository.RestaurantImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class RestaurantImageServiceImpl implements RestaurantImageService {

    private static final String UPLOAD_PATH = "src/main/imagefiles/";
    private final RestaurantImageRepository restaurantImageRepository;


    @Autowired
    public RestaurantImageServiceImpl(RestaurantImageRepository restaurantImageRepository) {
        this.restaurantImageRepository = restaurantImageRepository;
    }

    @Override
    public RestaurantImage saveRestaurantImage(Restaurant restaurant) throws IOException {
        RestaurantImage restaurantImage = new RestaurantImage();
        String prompt = restaurant.getRestaurantTypes().getFirst().getType() + "restaurant facade";
        byte[] image = restaurantImage.convertToByteArray(prompt);

        restaurantImage.setRestaurant(restaurant);
        restaurantImage.setImages(image);
        restaurantImage.setImagePath(restaurantImage.createImageFile(restaurant.getId()));

        return restaurantImageRepository.save(restaurantImage);
    }

    public void uploadFile(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("Empty file");
        }

        Path uploadPath = Paths.get(UPLOAD_PATH);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve("usr_img.jpg");
        Files.copy(file.getInputStream(), filePath);

        // You can do additional processing or save the file path to a database here
        RestaurantImage restaurantImage = new RestaurantImage();
        restaurantImage.setImagePath(filePath.toString());
        restaurantImageRepository.save(restaurantImage);
    }

    @Override
    public void uploadFileWithId(MultipartFile file, Long id) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("Empty file");
        }

        Path uploadPath = Paths.get(UPLOAD_PATH);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(String.format("%s/usr_img.jpg", id));
        Files.copy(file.getInputStream(), filePath);
    }
}
