package org.mindswap.springtheknife.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mindswap.springtheknife.service.RestaurantImageService;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor
public class RestaurantImage {

   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/

    private byte[] images;


    public void setImages(String prompt) {
        String imageData = RestaurantImageService.getImageDataFromAPI(prompt);
    }


}
