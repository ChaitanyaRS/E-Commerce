package com.ecommerce.orderservice.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Idempotency")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Idempotency {
    @Indexed(unique = true)
    private String key;
    private String response;
    private long timestamp;
}
