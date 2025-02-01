package com.ecommerce.productservice.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {
    private String productName;
    private String description;
    private double price;
    private int quantity;
    private String category;
    private String imageLink;
    private int ratings;
}
