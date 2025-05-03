package com.ecommerce.orderservice.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    //imageLink, productName, description, quantity, price, timestamp
    private int pid;
    private String imageLink;
    private String productName;
    private String description;
    private int quantity;
    private double price;
    private long timestamp;
}
