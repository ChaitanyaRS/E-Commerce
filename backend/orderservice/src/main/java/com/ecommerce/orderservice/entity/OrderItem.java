package com.ecommerce.orderservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class OrderItem {
    private int prodId;
    private int quantity;
    private double totalPrice;

    public OrderItem(int prodId, int quantity, double totalPrice) {
        this.prodId = prodId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

}
