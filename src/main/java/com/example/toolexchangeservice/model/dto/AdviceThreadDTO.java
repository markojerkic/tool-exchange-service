package com.example.toolexchangeservice.model.dto;

import lombok.Data;

import java.util.UUID;
import java.util.Date;

@Data
public class AdviceThreadDTO {
    private Long id;
    private String title;
    private String userCreated;
    private String details;
	private Date lastModified;
    private UUID thumbnailImageUuid;
}
