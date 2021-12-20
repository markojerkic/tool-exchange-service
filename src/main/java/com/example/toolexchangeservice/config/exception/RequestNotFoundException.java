package com.example.toolexchangeservice.config.exception;

import com.example.toolexchangeservice.repository.RequestRepository;

public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException(String msg) {
        super(msg);
    }
}
