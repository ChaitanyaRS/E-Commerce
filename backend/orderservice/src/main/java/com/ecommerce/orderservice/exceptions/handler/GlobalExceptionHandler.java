package com.ecommerce.orderservice.exceptions.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.orderservice.exceptions.OrderNotPlaced;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(OrderNotPlaced.class)
    public ResponseEntity<String> orderNotPlaced(OrderNotPlaced ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    
}
