package org.mindswap.springtheknife.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mindswap.springtheknife.service.RestaurantImageService;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte[] images;

    private String imagePath;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


    public String setImages(String prompt, Long id, String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        images = mapper.convertValue(RestaurantImageService.getImageDataFromAPI(prompt), byte[].class);
        ByteArrayInputStream stream = new ByteArrayInputStream(images);
        File file = new File("src/main/imagefiles/" + id + "/");
        file.mkdirs();
        ImageIO.write(ImageIO.read(stream), "png", new File("src/main/imagefiles/" + id + "/" + fileName + ".png"));
        return "src/main/imagefiles/" + id + "/" + fileName + ".png";
    }


}
