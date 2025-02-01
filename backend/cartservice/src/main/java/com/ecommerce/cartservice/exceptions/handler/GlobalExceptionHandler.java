package com.ecommerce.cartservice.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.cartservice.exceptions.CartNotFound;
import com.ecommerce.cartservice.exceptions.NoItemFound;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoItemFound.class)
    public ResponseEntity<String> noItemFoundEx(NoItemFound ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartNotFound.class)
    public ResponseEntity<String> noCartFoundEx(CartNotFound ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
