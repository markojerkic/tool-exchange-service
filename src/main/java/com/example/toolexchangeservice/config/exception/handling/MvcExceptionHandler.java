package com.example.toolexchangeservice.config.exception.handling;

import com.example.toolexchangeservice.config.exception.EmailAlreadyExistsException;
import com.example.toolexchangeservice.config.exception.ImageNotFoundException;
import com.example.toolexchangeservice.config.exception.UsernameAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class MvcExceptionHandler {

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Slika nije pronađena")
    public void imageNotFound(HttpServletRequest req, Exception e) {
        log.info("Image not found", e);
    }


    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Korisničko ime ili lozinka nisu točni")
    public void handleBadCredentialsException(HttpServletRequest request, Exception e) {
        log.info("Bad credentials", e);
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(HttpServletRequest request, Exception e) {
        log.info("Email already exists", e);
        return ResponseEntity.badRequest().body(new ErrorResponse("Email adresa se već koristi", "email"));
    }
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(HttpServletRequest request, Exception e) {
        log.info("Username already exists", e);
        return ResponseEntity.badRequest().body(new ErrorResponse("Email adresa se već koristi", "username"));
    }
}
