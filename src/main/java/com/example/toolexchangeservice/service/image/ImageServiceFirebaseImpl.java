package com.example.toolexchangeservice.service.image;

import com.example.toolexchangeservice.config.exception.ImageNotFoundException;
import com.example.toolexchangeservice.model.entity.Image;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.util.Objects;

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
    public void saveImageFile(Image image) {
        Storage storage = storageOptions.getService();
        BlobId blobId = BlobId.of(this.bucketName, ImageUtil.getImageFileName(image));
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, image.getBytes());
    }

    @Override
    @Async
    public void deleteImageFile(Image image) {
        Storage storage = storageOptions.getService();
        BlobId blobId = BlobId.of(this.bucketName, ImageUtil.getImageFileName(image));

        storage.delete(blobId);
    }

    @Override
    public Image getImageFile(Image image) {
        Storage storage = storageOptions.getService();

        Blob blob = storage.get(BlobId.of(this.bucketName, ImageUtil.getImageFileName(image)));
        if (Objects.isNull(blob)) {
            throw new ImageNotFoundException("Slika nije pronađena");
        }
        byte[] imageBytes = blob.getContent();
        image.setBytes(imageBytes);

        return image;
    }
}
