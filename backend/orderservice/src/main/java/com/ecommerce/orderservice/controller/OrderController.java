package com.ecommerce.orderservice.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.ecommerce.orderservice.utility.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.orderservice.service.OrderService;
import com.ecommerce.orderservice.utility.OrderDto;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place-order")
    public CompletableFuture<ResponseEntity<List<OrderInfo>>> placeOrder(@RequestHeader("Idempotency-key") String key, @RequestBody OrderDto dto) {
        return orderService.placeOrder(key, dto);
    }

    @GetMapping("/get-orders/{userId}")
    public CompletableFuture<ResponseEntity<List<OrderInfo>>> getAllOrderForUser(@PathVariable int userId){
        return orderService.getOrdersByUserId(userId);
    }
}
