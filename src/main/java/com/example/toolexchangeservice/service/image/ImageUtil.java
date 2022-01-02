package com.example.toolexchangeservice.service.image;

import com.example.toolexchangeservice.model.ImageFileExtension;

import java.util.Locale;
import java.util.UUID;

public class ImageUtil {
    public static String getImageFileName(UUID uuid, ImageFileExtension imageFileExtension) {
        return uuid.toString() + "." + imageFileExtension.name().toLowerCase(Locale.ROOT);
    }
}
