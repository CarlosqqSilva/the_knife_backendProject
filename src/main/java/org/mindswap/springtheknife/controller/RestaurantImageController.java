package org.mindswap.springtheknife.controller;

import org.mindswap.springtheknife.service.restaurantimage.RestaurantImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping(path = "api/v1/restaurants/img")
public class RestaurantImageController {

    private final RestaurantImageService restaurantImageService;

    @Autowired
    public RestaurantImageController(RestaurantImageService restaurantImageService) {
        this.restaurantImageService = restaurantImageService;
    }

    @GetMapping("/upload")
    public String showFileUploadPage() {
        return "upload";
    }

    @PostMapping("api/v1/restaurants/img/upload")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) {
        try {
            restaurantImageService.uploadFile(file);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("api/v1/restaurants/img/upload/{id}")
    public ResponseEntity<String> fileUploadWithId(@RequestParam("file") MultipartFile file, Long id) {
        try {
            restaurantImageService.uploadFile(file);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
