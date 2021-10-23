package com.example.toolexchangeservice.config.exception.handling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {
    private String message;
    private String reason;
}
