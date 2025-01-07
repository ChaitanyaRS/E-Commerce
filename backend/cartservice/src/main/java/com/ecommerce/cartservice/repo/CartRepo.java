package com.ecommerce.cartservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.entity.CartItem;

@Repository
public interface CartRepo extends JpaRepository<Cart,Integer>{

    Optional<Cart> findByUserId(int userId);
    
}
