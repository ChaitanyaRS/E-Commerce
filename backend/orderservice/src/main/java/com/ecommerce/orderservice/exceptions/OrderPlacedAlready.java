package com.ecommerce.orderservice.exceptions;

public class OrderPlacedAlready extends RuntimeException{
    public OrderPlacedAlready(String msg){
        super(msg);
    }
}
