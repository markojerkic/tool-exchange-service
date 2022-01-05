package com.example.toolexchangeservice.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AdvertPreviewDTO {
    private Long id;
    private String title;
    private String userCreated;
    private String details;
    private UUID thumbnailImageUuid;
}
