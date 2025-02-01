package com.ecommerce.cartservice.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.cartservice.entity.CartItem;
import com.ecommerce.cartservice.entity.ProductDto;
import com.ecommerce.cartservice.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    
    @PostMapping("/add-item")
    public ResponseEntity<String> addItem(@RequestBody ProductDto dto){
        return cartService.addItem(dto);
    }

    @PostMapping("/remove-item")
    public ResponseEntity<String> removeFromCart(@RequestBody ProductDto dto){
        return cartService.removeFromCart(dto);
    }

    @GetMapping("get-cartitems/{userId}")
    public ResponseEntity<List<CartItem>> getItemsInCartOfUser(@PathVariable int userId){
        return cartService.getCartItemsforUser(userId);
    }
}
