package com.ecommerce.cartservice.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
    @CreationTimestamp
    @Column(name = "created_at",updatable = false,nullable = false)
    private LocalDateTime createdAt;
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

    //Checks if the cart has specific item.
    public boolean checkItemAvailabilityInCart(int pId){
        return cartItemsList.stream().anyMatch(item -> item.getPId() == pId);
    }
}
