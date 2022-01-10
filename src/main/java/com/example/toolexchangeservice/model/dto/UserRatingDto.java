package com.example.toolexchangeservice.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserRatingDto {
    private Long id;
    private String fromUser;
    private String aboutUser;
    private Integer mark;
    private Date lastModified;
}
