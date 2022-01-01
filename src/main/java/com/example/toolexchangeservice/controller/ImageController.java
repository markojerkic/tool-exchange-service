package com.example.toolexchangeservice.controller;


import com.example.toolexchangeservice.model.entity.Image;
import com.example.toolexchangeservice.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    @Value("${image.server.location}")
    private String imageServerLocation;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public List<Image> saveImages(@RequestParam("images") MultipartFile[] images) {
        return this.imageService.saveImages(images);
    }

    @GetMapping("{uuid}")
    public ResponseEntity<?> getImageByUuid(@PathVariable UUID uuid) {
        Image image = this.imageService.getImageByUuid(uuid);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, this.imageServerLocation + "/" +
                        image.getUuid() + "/" + image.getImageFileExtension()).build();
    }

    @DeleteMapping("{uuid}")
    public void deleteImage(@PathVariable UUID uuid) {
        this.imageService.deleteImageByUUID(uuid);
    }
}
