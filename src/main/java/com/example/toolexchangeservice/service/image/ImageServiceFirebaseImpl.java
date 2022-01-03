package com.example.toolexchangeservice.service.image;

import com.example.toolexchangeservice.config.exception.ImageNotFoundException;
import com.example.toolexchangeservice.config.exception.ImageStorageException;
import com.example.toolexchangeservice.model.ImageFileExtension;
import com.example.toolexchangeservice.model.entity.Image;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Profile("prod")
@Slf4j
@Service
public class ImageServiceFirebaseImpl implements ImageFileService {
    private final String secretLocation;
    private final String projectId;
    private final String bucketName;
    private StorageOptions storageOptions;

    public ImageServiceFirebaseImpl(@Value("${project.id}") String projectId,
                                    @Value("${secret.location}") String secretLocation,
                                    @Value("${bucket.name}") String bucketName) {
        this.secretLocation = secretLocation;
        this.projectId = projectId;
        this.bucketName = bucketName;
    }

    @PostConstruct
    private void initializeFirebase() throws Exception {
        FileInputStream serviceAccount =
                new FileInputStream(this.secretLocation);

        this.storageOptions = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
    }

    @Override
    @Async
    public void saveImageFile(MultipartFile image, UUID uuid, ImageFileExtension fileExtension) {
        Storage storage = storageOptions.getService();
        BlobId blobId = BlobId.of(this.bucketName, ImageUtil.getImageFileName(uuid, fileExtension));
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        try {
            storage.create(blobInfo, image.getBytes());
        } catch (IOException e) {
            log.error("Greška prilikom spremanja slike", e);
            throw new ImageStorageException(e);
        }

        Image imageObj = new Image();
        imageObj.setUuid(uuid);
        imageObj.setImageFileExtension(fileExtension);

    }

    @Override
    @Async
    public void deleteImageFile(UUID uuid, ImageFileExtension imageFileExtension) {
        Storage storage = storageOptions.getService();
        BlobId blobId = BlobId.of(this.bucketName, ImageUtil.getImageFileName(uuid, imageFileExtension));

        storage.delete(blobId);
    }

    @Override
    public Image getImageFile(UUID uuid, ImageFileExtension fileExtension) {
        Storage storage = storageOptions.getService();

        Blob blob = storage.get(BlobId.of(this.bucketName, ImageUtil.getImageFileName(uuid, fileExtension)));
        if (Objects.isNull(blob)) {
            throw new ImageNotFoundException("Slika nije pronađena");
        }
        byte[] imageBytes = blob.getContent();

        Image image = new Image();
        image.setUuid(uuid);
        image.setImageFileExtension(fileExtension);
        image.setBytes(imageBytes);

        return image;
    }
}
