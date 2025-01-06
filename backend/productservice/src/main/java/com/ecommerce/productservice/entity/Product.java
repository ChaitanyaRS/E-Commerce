package com.ecommerce.productservice.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Table(name = "products")
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;
    private String productName;
    private String description;
    private double price;
    private int quantity;
    private String imageLink;
    @JoinColumn(name = "c_id")
    @JsonManagedReference
    @ManyToOne
    private Category category;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "r_id")
    private Rating rating;

    public Product(String productName, String description, double price, int quantity, String imageLink,
            Category category, Rating rating) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageLink = imageLink;
        this.category = category;
        this.rating = rating;
    }

    

}
