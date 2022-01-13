package com.example.toolexchangeservice.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AdviceDTO {
    private Long id;
    private String message;
    private Date lastModified;
    private String userCreated;
    private Boolean isBest;
    private Integer numLikes;
    private Boolean isLikedByMe;
}
