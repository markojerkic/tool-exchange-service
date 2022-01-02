package com.example.toolexchangeservice.config.exception;

public class ImageStorageException extends RuntimeException {
    public ImageStorageException(String message) {
        super(message);
    }

    public ImageStorageException(Throwable cause) {
        super(cause);
    }
}
