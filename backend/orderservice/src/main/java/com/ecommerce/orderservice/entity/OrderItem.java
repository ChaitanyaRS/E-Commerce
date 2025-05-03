package com.ecommerce.orderservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class OrderItem {
//    private int oId;
    private int prodId;
    private int quantity;
    private double totalPrice;
    private int userId; 
    
    public OrderItem(int prodId, int quantity, double totalPrice,int userId) {
        this.prodId = prodId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.userId = userId;
    }

}
