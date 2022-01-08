package com.example.toolexchangeservice.model.dto;

import lombok.Data;

@Data
public class UserPreviewDTO {
    private Long id;
    private String username;
    private Boolean isDisabled;
    private String firstName;
    private String lastName;
    private String formattedAddress;
    private String email;
}
