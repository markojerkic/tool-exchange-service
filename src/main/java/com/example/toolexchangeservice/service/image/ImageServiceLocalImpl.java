package com.example.toolexchangeservice.service.image;

import com.example.toolexchangeservice.config.exception.ImageNotFoundException;
import com.example.toolexchangeservice.config.exception.ImageStorageException;
import com.example.toolexchangeservice.model.entity.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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

    @Async
    public void saveImageFile(Image image) {
        try {

            this.createBaseDirIfNotExists();

            Files.copy(image.getFile().getInputStream(), this.root.resolve(ImageUtil.getImageFileName(image.getUuid(),
                    image.getImageFileExtension())));

            log.info("{} saved at {}", image, this.root.resolve(ImageUtil.getImageFileName(image.getUuid(),
                    image.getImageFileExtension())));
        } catch (IOException e) {
            log.error("Greška prilikom spremanja slike", e);
            throw new ImageStorageException("Greška prilikom spremanja slike");
        }
    }

    public Image getImageFile(Image image) {
        try {
            image.setBytes(Files.readAllBytes(this.root.resolve(ImageUtil.getImageFileName(image))));
            return image;
        } catch (IOException e) {
            log.error("Image {} not found", this.root.resolve(ImageUtil.getImageFileName(image)));
            throw this.imageNotFound(image);
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

    public void deleteImageFile(Image image) {
        try {
            Files.delete(this.root.resolve(ImageUtil.getImageFileName(image)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
