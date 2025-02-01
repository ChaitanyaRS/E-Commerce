package com.ecommerce.orderservice.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

//For generating auto incrementing id's
@Document(collection = "counters")
@Data
public class Counter {
    private String id;
    private int seq;
}
