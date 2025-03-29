package com.ecommerce.userservice.exceptions.handler;

import com.ecommerce.userservice.exceptions.PhoneAlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.core.AuthenticationException;

import com.ecommerce.userservice.exceptions.EmailAlreadyExists;
import com.ecommerce.userservice.exceptions.UserNotFound;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity<String> emailAlreadyExists(EmailAlreadyExists ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhoneAlreadyExists.class)
    public ResponseEntity<String> phoneAlreadyExists(PhoneAlreadyExists ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<String> userNotFound(UserNotFound ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> badCredentials(BadCredentialsException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + ex.getMessage());
    }
}
