package org.mindswap.springtheknife.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mindswap.springtheknife.service.RestaurantImageService;
import org.springframework.data.annotation.Id;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@Getter
@NoArgsConstructor
public class RestaurantImage {

   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/

    private byte[] images;


    public String setImages(String prompt, String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        images = mapper.convertValue(RestaurantImageService.getImageDataFromAPI(prompt), byte[].class);
        ByteArrayInputStream bis = new ByteArrayInputStream(images);
        ImageIO.write(ImageIO.read(bis), "png", new File("src/main/imagefiles/" + fileName + ".png"));
        return "src/main/imagefiles/" + fileName + ".png";
    }


}
