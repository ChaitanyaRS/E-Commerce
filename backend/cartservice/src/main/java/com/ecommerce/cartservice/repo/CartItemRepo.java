package com.ecommerce.cartservice.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.entity.CartItem;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Integer>{

    @Query(value = "SELECT * FROM cart_item ci WHERE ci.p_id = :pId AND ci.cart_id = :cartId",nativeQuery = true)
    Optional<CartItem> findBypId(int pId,int cartId);

    // @Query(value = "SELECT * FROM cart_item ci WHERE ci.cart_id = :cartId",nativeQuery = true)
    // Optional<List<CartItem>> findByCartId(int cartId);

}
