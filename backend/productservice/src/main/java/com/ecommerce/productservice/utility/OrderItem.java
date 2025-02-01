package com.ecommerce.productservice.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class OrderItem {
    private int prodId;
    private int quantity;
    private double totalPrice;


}
