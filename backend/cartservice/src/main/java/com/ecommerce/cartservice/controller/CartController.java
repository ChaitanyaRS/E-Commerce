package com.ecommerce.cartservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.cartservice.entity.ProductDto;
import com.ecommerce.cartservice.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    
    @PostMapping("/add-item")
    public ResponseEntity<String> addItem(@RequestBody ProductDto dto){
        System.out.println("PID in c : "+dto.getProdId());
        System.out.println("UID in c : "+dto.getUserId());
        return cartService.addItem(dto);
    }
}
