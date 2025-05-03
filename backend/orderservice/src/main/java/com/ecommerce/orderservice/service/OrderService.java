package com.ecommerce.orderservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.ecommerce.orderservice.entity.OrderItem;
import com.ecommerce.orderservice.exceptions.OrderNotPlaced;
import com.ecommerce.orderservice.exceptions.OrderPlacedAlready;
import com.ecommerce.orderservice.feign.CartFeignService;
import com.ecommerce.orderservice.feign.ProductFeignService;
import com.ecommerce.orderservice.utility.OrderInfo;
import com.ecommerce.orderservice.utility.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.orderservice.entity.Idempotency;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.repo.OrderRepo;
import com.ecommerce.orderservice.utility.OrderDto;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private SequenceGeneratorService seq;

    @Autowired
    private IdempotencyService idService;

    @Autowired
    private MongoTemplate template;

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private ProductFeignService prodFeignService;

    @Autowired
    private CartFeignService cartFeignService;

    @Async("customExecutor")
    @Transactional
    public CompletableFuture<ResponseEntity<List<OrderInfo>>> placeOrder(String key, OrderDto dto) {
        // Implemented Idempotency key method to prevent duplicate order.
        if (idService.isDuplicate(key)) {
//            return ResponseEntity.ok(idService.getPreviousResponse(key));
            throw new OrderPlacedAlready("Order already placed.");
        }

        for(OrderItem item : dto.getItems()){
            for(int i = 0; i < item.getQuantity();i++) {
                ResponseEntity<Integer> prod = prodFeignService.checkProdAvailability(item.getProdId());
                if (prod.getStatusCode().is4xxClientError())
                    throw new OrderNotPlaced("Product Out of Stock !!");
            }
        }

        int oId = seq.generateSequence("oId");
        long epochTime = System.currentTimeMillis() / 1000;
        Order order = new Order(oId, dto.getUserId(), dto.getAddress(), dto.getContactNo(),dto.getPincode(), epochTime, dto.getItems(), dto.getTotalPrice());
        Order placedOrder = orderRepo.save(order);

        //Delete cart after placing order as it will be empty.
        cartFeignService.emptyCart(dto.getUserId());


        log.info("Order "+oId+"stored in the database.");
        String response = oId + " Placed !!";
        idService.saveIdempotencyKey(new Idempotency(key, response, epochTime));

        //send an event to product service to notify about order product and quantity.
        kafkaService.sendEvent(placedOrder.getItems());
        log.info("Kafka event sent to product service.");
        List<OrderInfo> orders = null;
        try {
            orders = getOrdersByUserId(dto.getUserId()).get().getBody();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(ResponseEntity.ok(orders));
    }

    public CompletableFuture<ResponseEntity<List<OrderInfo>>> getOrdersByUserId(int userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        List<Order> orders = template.find(query, Order.class);
        List<OrderItem> orderItems = orders.stream().flatMap(order -> order.getItems().stream()).toList();

        //Get the product info of all the products and make the list of it.
        List<OrderInfo> orderList = new ArrayList<>();
        for(Order order : orders){
            if(order.getUserId() == userId){
                for(OrderItem item : order.getItems()){
                    //imageLink, productName, description, quantity, price, timestamp
                    ProductInfo pInfo = prodFeignService.getProductById(item.getProdId()).getBody();
                    orderList.add(new OrderInfo(pInfo.getPId(),pInfo.getImageLink(), pInfo.getProductName(), pInfo.getDescription(), item.getQuantity(),item.getTotalPrice(),order.getTimestamp()));
                }
            }
        }
//        for(OrderItem item : orderItems){
//            //imageLink, productName, description, quantity, price, timestamp
//            ProductInfo pInfo = prodFeignService.getProductById(item.getProdId()).getBody();
//            orderList.add(new OrderInfo(pInfo.getImageLink(),pInfo.getProductName(),pInfo.getDescription(),item.getQuantity(),item.getTotalPrice(),item.get))
//        }
        if (orders.isEmpty()) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(Collections.emptyList()));
        }
        return CompletableFuture.completedFuture(ResponseEntity.ok(orderList));
    }
}
