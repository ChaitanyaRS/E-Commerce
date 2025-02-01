package com.ecommerce.cartservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.entity.CartItem;
import com.ecommerce.cartservice.entity.ProductDto;
import com.ecommerce.cartservice.exceptions.CartNotFound;
import com.ecommerce.cartservice.exceptions.NoItemFound;
import com.ecommerce.cartservice.repo.CartItemRepo;
import com.ecommerce.cartservice.repo.CartRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    public ResponseEntity<String> addItem(ProductDto dto) {

        Cart cart = cartRepo.findByUserId(dto.getUserId()).orElse(new Cart(dto.getUserId()));

        CartItem item = cartItemRepo.findBypId(dto.getProdId(), cart.getCartId())
                .orElse(new CartItem(dto.getProdId(), 0, cart));

        // update the quantity
        item.setQuantity(item.getQuantity() + 1);

        cart.addItemToList(item);

        Cart savedCart = cartRepo.save(cart);

        item.setCart(savedCart);

        cartItemRepo.save(item);

        log.info("Product added to cart by user " + dto.getUserId());

        return ResponseEntity.ok().body("Added !!");
    }
    
    //create remove from cart
    //remove item from cart
    //check if cart has any other product
    //if not then delete the cart as well

    public ResponseEntity<String> removeFromCart(ProductDto dto) {
        Cart cart = cartRepo.findByUserId(dto.getUserId()).get();
        
        Optional<CartItem> item = cartItemRepo.findBypId(dto.getProdId(), cart.getCartId());
        if(item.isEmpty())
            throw new NoItemFound("Item not found in cart !!");

        int indexOfItem = cart.getCartItemsList().indexOf(item.get());

        if(item.get().getQuantity() > 1){
            item.get().setQuantity(item.get().getQuantity() - 1);
            cart.getCartItemsList().set(indexOfItem, item.get());
        }else{
            log.info("Item "+item.get() + "removed from cart ");
            cart.getCartItemsList().remove(item.get());
        }

        if(cart.getCartItemsList().isEmpty()){
            cartRepo.delete(cart);
            log.info("Cart deleted for user "+dto.getUserId());
        }else
            cartRepo.save(cart);

        return ResponseEntity.ok().body("Removed !!");
    }

    public ResponseEntity<List<CartItem>> getCartItemsforUser(int userId) {
        Cart cart = cartRepo.findByUserId(userId).orElseThrow(() -> new CartNotFound("Cart Not found for this user"));
        if(cart.getCartItemsList().isEmpty())
            throw new NoItemFound("Cart is Empty !!"); 
        else{
            List<CartItem> cartItems = cart.getCartItemsList(); 
            return ResponseEntity.ok().body(cartItems);
        }
    }



}
