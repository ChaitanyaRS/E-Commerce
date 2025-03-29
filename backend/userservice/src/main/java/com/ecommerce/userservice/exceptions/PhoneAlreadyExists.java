package com.ecommerce.userservice.exceptions;

public class PhoneAlreadyExists  extends RuntimeException {
    
    public PhoneAlreadyExists(String message) {
        super(message);
    }
}
