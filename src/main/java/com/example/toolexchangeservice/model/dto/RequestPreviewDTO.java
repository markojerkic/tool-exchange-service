package com.example.toolexchangeservice.model.dto;

import lombok.Data;

@Data
public class RequestPreviewDTO {
    private Long id;
    private String title;
    private String details;
    private String userCreated;
    private Boolean isBest;
}
