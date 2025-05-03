package com.ecommerce.cartservice.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadPoolExecutor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private int pId;
    private String productName;
    private String description;
    private double price;
    private int quantity;
    private String category;
    private String imageLink;
    private int ratings;
}
