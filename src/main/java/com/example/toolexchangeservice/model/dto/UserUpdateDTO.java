package com.example.toolexchangeservice.model.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
