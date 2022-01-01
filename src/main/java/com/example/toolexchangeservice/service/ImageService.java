package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.config.exception.ImageNotFoundException;
import com.example.toolexchangeservice.model.ImageFileExtension;
import com.example.toolexchangeservice.model.entity.Image;
import com.example.toolexchangeservice.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;
    private final RestTemplate restTemplate;

    @Value("${image.server.location}")
    private String imageServerLocation;

    public ImageService(ImageRepository imageRepository, RestTemplate restTemplate) {
        this.imageRepository = imageRepository;
        this.restTemplate = restTemplate;
    }

    public List<Image> saveImages(MultipartFile[] images) {
        return Arrays.stream(images).map(this::saveImageFile)
                .filter(image -> image.getUuid() != null)
                .collect(Collectors.toList());
    }

    private Image saveImageFile(MultipartFile imageFile) {
        Image image = new Image();
        String extension = Objects.requireNonNull(imageFile.getOriginalFilename())
                .substring(imageFile.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase(Locale.ROOT);

        ImageFileExtension imageFileExtension;
        switch (extension) {
            case "png":
                imageFileExtension = ImageFileExtension.PNG;
                break;
            case "jpeg":
                imageFileExtension = ImageFileExtension.JPEG;
                break;
            case "jpg":
                imageFileExtension = ImageFileExtension.JPG;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + extension);
        }
        image.setImageFileExtension(imageFileExtension);

        Image savedImage = this.imageRepository.save(image);
        savedImage.setFile(imageFile);

        this.postImageFile(savedImage);
        return savedImage;
    }

    @Async
    void postImageFile(Image image) {
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        try {
            body.set("image", new ByteArrayResource(image.getFile().getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        try {
            this.restTemplate.postForLocation(this.imageServerLocation + "/" + image.getUuid()
                    + "/" + image.getImageFileExtension(), requestEntity);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            this.deleteImageByUUID(image.getUuid());
        }
    }

    public Image getImageByUuid(UUID imageUuid) {
        return this.imageRepository.findById(imageUuid).orElseThrow(() -> this.imageNotFound(imageUuid));
    }

    private ImageNotFoundException imageNotFound(UUID imageUuid) {
        return new ImageNotFoundException("Slika " + imageUuid + " nije pronađena");
    }

    public void deleteImageObject(UUID uuid) {
        this.imageRepository.deleteById(uuid);
    }

    public void deleteImageByUUID(UUID uuid) {
        Image image = this.imageRepository.findById(uuid).orElseThrow(() -> this.imageNotFound(uuid));

        this.imageRepository.deleteById(uuid);
        this.restTemplate.delete(this.imageServerLocation + "/" + uuid + "/" + image.getImageFileExtension());
    }
}