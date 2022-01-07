package com.example.toolexchangeservice.config.exception;

public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException(String msg) {
        super(msg);
    }
}
