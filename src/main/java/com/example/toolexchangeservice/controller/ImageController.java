package com.example.toolexchangeservice.controller;


import com.example.toolexchangeservice.model.entity.Image;
import com.example.toolexchangeservice.service.image.ImageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/image")
@CrossOrigin("*")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public List<Image> saveImages(@RequestParam("images") MultipartFile[] images) {
        return this.imageService.saveImages(images);
    }

    @GetMapping("{uuid}")
    public ResponseEntity<byte[]> getFile(@PathVariable UUID uuid) {
        Image image = this.imageService.getImageFile(uuid);

        HttpHeaders headers = new HttpHeaders();
        switch (image.getImageFileExtension()) {
            case JPEG:
            case JPG:
                headers.setContentType(MediaType.IMAGE_JPEG);
                break;
            case PNG:
                headers.setContentType(MediaType.IMAGE_PNG);
                break;
            default:
                throw new IllegalArgumentException();
        }

        return new ResponseEntity<>(image.getBytes(), headers, HttpStatus.OK);
    }

    @DeleteMapping("{uuid}")
    public void deleteImage(@PathVariable UUID uuid) {
        this.imageService.deleteImageByUUID(uuid);
    }
}
