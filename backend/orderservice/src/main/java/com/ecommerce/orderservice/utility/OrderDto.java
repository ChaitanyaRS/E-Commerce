package com.ecommerce.orderservice.utility;

import java.util.List;

import com.ecommerce.orderservice.entity.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
@AllArgsConstructor
public class OrderDto {
    private int userId;
    private String address;
    private List<OrderItem> items;
    private double totalPrice;

}
