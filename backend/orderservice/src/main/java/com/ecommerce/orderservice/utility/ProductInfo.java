package com.ecommerce.orderservice.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo {
    private int pId;
    private String productName;
    private String description;
    private double price;
    private int quantity;
    private String category;
    private String imageLink;
    private int ratings;
}
