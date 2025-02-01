package com.ecommerce.orderservice.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "ecommerce")
@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Order {
    
    @Transient
    public static final String SEQUENCE_NAME = "orderss_sequence";

    @Id
    private int oId;
    private int userId;
    private String address;
    //using long as it is more beneficial from performance point of view as datetime is stored in epoch time format in mongoDB
    private long timestamp;
    private List<OrderItem> items;
    private double totalPrice;

    public Order(int userId, String address, long timestamp, List<OrderItem> items, double totalPrice) {
        this.userId = userId;
        this.address = address;
        this.timestamp = timestamp;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    
}
