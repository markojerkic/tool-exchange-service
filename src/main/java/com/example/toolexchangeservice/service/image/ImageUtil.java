package com.example.toolexchangeservice.service.image;

import com.example.toolexchangeservice.model.ImageFileExtension;
import com.example.toolexchangeservice.model.entity.Image;

import java.util.Locale;
import java.util.UUID;

public class ImageUtil {
    public static String getImageFileName(UUID uuid, ImageFileExtension imageFileExtension) {
        return uuid.toString() + "." + imageFileExtension.name().toLowerCase(Locale.ROOT);
    }

    public static String getImageFileName(Image image) {
        return image.getUuid().toString() + "." + image.getImageFileExtension().name().toLowerCase(Locale.ROOT);
    }
}
