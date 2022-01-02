package com.example.toolexchangeservice.service.image;

import com.example.toolexchangeservice.config.exception.ImageNotFoundException;
import com.example.toolexchangeservice.config.exception.ImageStorageException;
import com.example.toolexchangeservice.model.ImageFileExtension;
import com.example.toolexchangeservice.model.entity.Image;
import com.example.toolexchangeservice.service.ImageFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
@Profile("dev")
public class ImageServiceLocalImpl implements ImageFileService {

    private final Path root = Paths.get("images");

    private void createBaseDirIfNotExists() throws IOException {
        File dir = this.root.toFile();
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                throw new IOException();
            }
        }
    }

    public void saveImageFile(MultipartFile image, UUID uuid, ImageFileExtension fileExtension) {
        Image savedImage = new Image();
        try {
            savedImage.setUuid(uuid);
            savedImage.setImageFileExtension(fileExtension);

            this.createBaseDirIfNotExists();

            Files.copy(image.getInputStream(), this.root.resolve(ImageUtil.getImageFileName(savedImage.getUuid(),
                    savedImage.getImageFileExtension())));

            log.info("{} saved at {}", savedImage, this.root.resolve(ImageUtil.getImageFileName(savedImage.getUuid(),
                    savedImage.getImageFileExtension())));
        } catch (IOException e) {
            log.error("Greška prilikom spremanja slike", e);
            throw new ImageStorageException("Greška prilikom spremanja slike");
        }
    }

    public Image getImageFile(UUID uuid, ImageFileExtension fileExtension) {
        try {
            Image image = new Image();
            image.setBytes(Files.readAllBytes(this.root.resolve(ImageUtil.getImageFileName(uuid, fileExtension))));
            return image;
        } catch (IOException e) {
            log.error("Image {} not found", this.root.resolve(ImageUtil.getImageFileName(uuid, fileExtension)));
            throw this.imageNotFound(uuid);
        }
    }

    public void updateImage(Image image) {
        File currentImage = new File(String.valueOf(this.root.resolve(ImageUtil.getImageFileName(image.getUuid(),
                image.getImageFileExtension()))));
        if (!currentImage.exists() ||
                !currentImage.renameTo(new File(String.valueOf(this.root
                        .resolve(ImageUtil.getImageFileName(image.getUuid(),
                                image.getImageFileExtension())))))) {
            throw this.imageNotFound(image);
        }

        //return updatedImage;
    }

    private ImageNotFoundException imageNotFound(Image image) {
        return new ImageNotFoundException("Slika " + image.getUuid() + " nije pronađena");
    }

    private ImageNotFoundException imageNotFound(UUID imageUuid) {
        return new ImageNotFoundException("Slika " + imageUuid + " nije pronađena");
    }

    public void deleteImageFile(UUID uuid, ImageFileExtension imageFileExtension) {
        try {
            Files.delete(this.root.resolve(ImageUtil.getImageFileName(uuid, imageFileExtension)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
