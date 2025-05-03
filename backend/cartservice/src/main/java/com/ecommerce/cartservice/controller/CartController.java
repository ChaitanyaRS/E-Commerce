package com.ecommerce.cartservice.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.utility.CartDto;
import com.ecommerce.cartservice.utility.CartItemDto;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.cartservice.entity.CartItem;
import com.ecommerce.cartservice.service.CartService;
import com.ecommerce.cartservice.utility.ProductDto;

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

    @DeleteMapping("/empty-cart/{userId}")
    public ResponseEntity<String> emptyCart(@PathVariable("userId") int userId){
        return cartService.emptyCartForUser(userId);
    }

    @GetMapping("get-cartitems/{userId}")
    public CompletableFuture<ResponseEntity<CartDto>> getItemsInCartOfUser(@PathVariable int userId){
        return cartService.getCartItemsforUser(userId);
    }

    @GetMapping("get-carts/{prodId}")
    public ResponseEntity<List<Cart>> updateCartAccToInvntry(@PathVariable("prodId") int prodId){
        return cartService.updateCartAccToInventory(prodId);
    }

    @PostMapping("/increase-qty")
    public CompletableFuture<ResponseEntity<CartDto>> increaseCartItemQuantityForUser(@RequestBody ProductDto dto){
        return cartService.increaseQty(dto);
    }

    @PostMapping("/decrease-qty")
    public CompletableFuture<ResponseEntity<CartDto>> decreaseCartItemQuantityForUser(@RequestBody ProductDto dto){
        return cartService.decreaseQty(dto);
    }
}
