package org.mindswap.springtheknife.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mindswap.springtheknife.utils.ImageApiHandler;

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

    @Setter
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


    public void setImages(String prompt) throws IOException {
        images = ImageApiHandler.getImageDataFromAPI(prompt).getBytes();
    }

    private String createImageFile(String prompt, String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        images = mapper.convertValue(ImageApiHandler.getImageDataFromAPI(prompt), byte[].class);
        ByteArrayInputStream stream = new ByteArrayInputStream(images);
        File file = new File("src/main/imagefiles/" + id + "/");
        file.mkdirs();
        ImageIO.write(ImageIO.read(stream), "png", new File("src/main/imagefiles/" + id + "/" + fileName + this.id + ".png"));
        return "src/main/imagefiles/" + id + "/" + fileName + ".png";
    }

}
