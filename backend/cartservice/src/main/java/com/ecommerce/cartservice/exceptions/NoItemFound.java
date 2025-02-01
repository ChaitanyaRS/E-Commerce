package com.ecommerce.cartservice.exceptions;

public class NoItemFound extends RuntimeException {
    public NoItemFound(String msg){
        super(msg);
    }
}
