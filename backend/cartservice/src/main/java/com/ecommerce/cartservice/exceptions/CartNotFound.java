package com.ecommerce.cartservice.exceptions;

public class CartNotFound extends RuntimeException {
    public CartNotFound(String msg){
        super(msg);
    }
}
