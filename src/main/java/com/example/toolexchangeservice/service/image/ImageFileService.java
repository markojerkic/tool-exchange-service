package com.example.toolexchangeservice.service.image;

import com.example.toolexchangeservice.model.entity.Image;

public interface ImageFileService {
    void saveImageFile(Image image);

    void deleteImageFile(Image image);

    Image getImageFile(Image image);
}
