package com.ecommerce.orderservice.exceptions;


public class OrderNotPlaced extends RuntimeException {
    public OrderNotPlaced(String msg){
        super(msg);
    }
}
