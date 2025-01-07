package com.ecommerce.cartservice.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;
    private int userId;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cartItemsList;

    public Cart(int userId) {
        this.userId = userId;
        this.cartItemsList = new ArrayList<>();
    }

    public void addItemToList(CartItem item) {
        if (this.cartItemsList.isEmpty()) {
            this.cartItemsList = new ArrayList<CartItem>();
            this.cartItemsList.add(item);
        }
        this.cartItemsList.add(item);
    }
}
