package com.ecommerce.cartservice.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.ecommerce.cartservice.feign.CartFeignClient;
import com.ecommerce.cartservice.utility.CartDto;
import com.ecommerce.cartservice.utility.CartItemDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.entity.CartItem;
import com.ecommerce.cartservice.exceptions.CartNotFound;
import com.ecommerce.cartservice.exceptions.NoItemFound;
import com.ecommerce.cartservice.repo.CartItemRepo;
import com.ecommerce.cartservice.repo.CartRepo;
import com.ecommerce.cartservice.utility.ProductDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartFeignClient client;
    private int userId;

    public ResponseEntity<String> addItem(ProductDto dto) {

    	//Check if cart is available, if not then create
        Cart cart = cartRepo.findByUserId(dto.getUserId()).orElse(new Cart(dto.getUserId()));
        
    	//Check if cart item is available, if not then create
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
    
    public ResponseEntity<String> removeFromCart(ProductDto dto) {
        Cart cart = cartRepo.findByUserId(dto.getUserId()).get();
        
        Optional<CartItem> item = cartItemRepo.findBypId(dto.getProdId(), cart.getCartId());
        
        if(item.isEmpty())
            throw new NoItemFound("Item not found in cart !!");

        int indexOfItem = cart.getCartItemsList().indexOf(item.get());

        //Quantity of product in the cart.
        int cartProdQuantity = item.get().getQuantity();
        if(cartProdQuantity <= 1) {
        	//If quantity is less than or equal to product 
        	log.info("Item "+item.get() + "removed from cart ");
        	cart.getCartItemsList().remove(item.get());
        }else {
        	//Reduce the quantity by 1.
        	item.get().setQuantity(item.get().getQuantity() - 1);
            cart.getCartItemsList().set(indexOfItem, item.get());
        }
       
        if(cart.getCartItemsList().isEmpty()){
            cartRepo.delete(cart);
            log.info("Cart deleted for user "+dto.getUserId());
        }else
            cartRepo.save(cart);

        return ResponseEntity.ok().body("Removed !!");
    }

//    public ResponseEntity<String> updateCarts(ProductDto dto){
//
//
//
//    	return ResponseEntity.ok("Done !!");
//    }
//
////    public ResponseEntity<List<CartItem>> getCartItemsforUser(int userId) {
////        Cart cart = cartRepo.findByUserId(userId).orElseThrow(() -> new CartNotFound("Cart Not found for this user"));
////        if(cart.getCartItemsList().isEmpty())
////            throw new NoItemFound("Cart is Empty !!");
////        else{
////            List<CartItem> cartItems = cart.getCartItemsList();
////            int totalPrice =
////            return ResponseEntity.ok().body(cartItems);
////        }
////    }

    @Async("customExecutor")
    public CompletableFuture<ResponseEntity<CartDto>> getCartItemsforUser(int userId) {

        Cart cart = cartRepo.findByUserId(userId).orElseThrow(() -> new CartNotFound("Cart Not found for this user"));
        if(cart.getCartItemsList().isEmpty())
            throw new NoItemFound("Cart is Empty !!");
        else{
            List<CartItem> cartItems = cart.getCartItemsList();
            List<CartItemDto> cartItemsDto = cartItems.stream().map(item -> client.getProductById(item.getPId()).getBody()).toList();
            for(int i = 0; i < cartItemsDto.size(); i++){
                cartItemsDto.get(i).setQuantity(cartItems.get(i).getQuantity());
            }
            double totalPrice = cartItemsDto.stream().mapToDouble(item -> item.getPrice()*item.getQuantity()).sum();
            return CompletableFuture.completedFuture(ResponseEntity.ok().body(new CartDto(cartItemsDto,totalPrice)));
        }
    }

    //This method updates the carts as per the product inventory.
    //// Implementation not yet completed. - Not sure need to check.
    /// Commenting @Async as I think multiple thread updating the same value will create issue or race condition.
//    @Async("customExecutor")
    @Transactional
    public synchronized ResponseEntity<List<Cart>> updateCartAccToInventory(int prodId){
        List<Cart> cartsList = cartRepo.findAll();
//        List<Cart> affectedCarts = cartsList.stream().filter(cart -> cart.getCartItemsList().stream().anyMatch(item -> item.getPId() == prodId)).toList();

        //You can sort it later according to the date of adding in cart or any other specific requirement.
        List<Cart> affectedCarts = cartsList.stream().filter(cart -> cart.checkItemAvailabilityInCart(prodId)).collect(Collectors.toList());

        //Gets the count of total items of specific prodId
        int countOfTotalItems = affectedCarts.stream().flatMap(cart -> cart.getCartItemsList().stream()).filter(item -> item.getPId() == prodId).mapToInt(CartItem::getQuantity).sum();

//        int countOfTotalItems = 0;
//        for(Cart cart : affectedCarts){
//            CartItem item = cart.getCartItemsList().stream().filter(i -> i.getPId() == prodId).findFirst().get();
//            countOfTotalItems += item.getQuantity();
//        }

        //Getting quantity of the required product.
        int qtyOfProd = client.getQuantityOfSpecProd(prodId).getBody();

        //if the total quantity of cart is greater that than the inventory then only do the sorting otherwise keep it as it is.
        if(countOfTotalItems < qtyOfProd){
            return ResponseEntity.ok(Collections.emptyList());
        }
        while(qtyOfProd >= 0){
            for(Cart cart : affectedCarts){
                CartItem item = cart.getCartItemsList().stream().filter(cartItem -> cartItem.getPId() == prodId).findFirst().get();
                int itemQty = item.getQuantity();
                if(qtyOfProd > itemQty){
                    qtyOfProd -= itemQty;
                }else{
                    item.setQuantity(qtyOfProd);
                    qtyOfProd = 0;
                    break;
                }
            }
        }
        cartRepo.saveAll(affectedCarts);

        return ResponseEntity.ok(affectedCarts);
    }

    public ResponseEntity<String> emptyCartForUser(int userId) {
        Cart cart = cartRepo.findByUserId(userId).orElseThrow(()->new CartNotFound("Cart List is empty"));

        cartRepo.delete(cart);

        log.info("Cart removed for user "+userId+" after placing order.");
        return ResponseEntity.ok("Cart Removed !!");
    }


    /// It checks the quantity of product in inventory then updates the cart accordingly.
    public CompletableFuture<ResponseEntity<CartDto>> increaseQty(ProductDto dto) {
        Optional<Integer> qtyOfProdInInventory = Optional.ofNullable(client.getQuantityOfSpecProd(dto.getProdId()).getBody());
        if(qtyOfProdInInventory.isEmpty()){
            throw new NoItemFound("Qty not received from product service !!");
        }
        Cart cart = cartRepo.findByUserId(dto.getUserId()).get();
        List<CartItem> cartItems = cart.getCartItemsList();
        CartItem itemInCart = cartItems.stream().filter(item -> item.getPId() == dto.getProdId()).findFirst().get();
        int qtyOfProdInCart = itemInCart.getQuantity();
        if(qtyOfProdInCart < qtyOfProdInInventory.get()){
           itemInCart.setQuantity(itemInCart.getQuantity()+1);
           cartRepo.save(cart);
           return getCartItemsforUser(dto.getUserId());
        }
        return getCartItemsforUser(dto.getUserId());
    }

    /// It checks the quantity of product in inventory then updates the cart accordingly.
    public CompletableFuture<ResponseEntity<CartDto>> decreaseQty(ProductDto dto) {
        Cart cart = cartRepo.findByUserId(dto.getUserId()).orElseThrow(() -> new NoItemFound("User not found !!"));
        List<CartItem> cartItems = cart.getCartItemsList();
        CartItem itemInCart = cartItems.stream().filter(item -> item.getPId() == dto.getProdId()).findFirst().get();
        int qtyOfProdInCart = itemInCart.getQuantity();
        if(qtyOfProdInCart <= 1){
            removeFromCart(dto);
        }else{
            itemInCart.setQuantity(itemInCart.getQuantity()-1);
            cartRepo.save(cart);
        }
        return getCartItemsforUser(dto.getUserId());
    }
}
