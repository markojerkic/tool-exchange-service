package com.example.toolexchangeservice.service.image;

import com.example.toolexchangeservice.config.exception.ImageNotFoundException;
import com.example.toolexchangeservice.model.ImageFileExtension;
import com.example.toolexchangeservice.model.entity.Image;
import com.example.toolexchangeservice.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageFileService imageFileService;

    public ImageService(ImageRepository imageRepository, RestTemplate restTemplate,
                        ImageFileService imageFileService) {
        this.imageRepository = imageRepository;
        this.imageFileService = imageFileService;
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

    public MediaType getContentType(ImageFileExtension fileExtension) {
        MediaType mediaType;
        switch (fileExtension) {
            case JPEG:
            case JPG:
                return MediaType.IMAGE_JPEG;
            case PNG:
                return MediaType.IMAGE_PNG;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void postImageFile(Image image) {
        this.imageFileService.saveImageFile(image);
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
        this.imageFileService.deleteImageFile(image);
    }

    public Image getImageFile(UUID uuid) {
        Image image = this.getImageByUuid(uuid);
        return this.imageFileService.getImageFile(image);
    }
}