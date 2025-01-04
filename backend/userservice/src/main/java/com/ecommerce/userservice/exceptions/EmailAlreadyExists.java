package com.ecommerce.userservice.exceptions;

public class EmailAlreadyExists extends RuntimeException {

    public EmailAlreadyExists(String message) {
        super(message);
    }
}
