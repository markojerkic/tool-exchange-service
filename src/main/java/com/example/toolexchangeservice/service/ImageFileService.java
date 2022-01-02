package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.model.ImageFileExtension;
import com.example.toolexchangeservice.model.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImageFileService {
    void saveImageFile(MultipartFile image, UUID uuid, ImageFileExtension fileExtension);
    void deleteImageFile(UUID uuid, ImageFileExtension imageFileExtension);
    Image getImageFile(UUID uuid, ImageFileExtension fileExtension);
}
