package com.ecommerce.orderservice.service;

import java.util.Collections;
import java.util.List;

import com.ecommerce.orderservice.entity.OrderItem;
import com.ecommerce.orderservice.exceptions.OrderNotPlaced;
import com.ecommerce.orderservice.feign.CartFeignService;
import com.ecommerce.orderservice.feign.ProductFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
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

    @Transactional
    public ResponseEntity<String> placeOrder(String key, OrderDto dto) {
        // Implemented Idempotency key method to prevent duplicate order.
        if (idService.isDuplicate(key)) {
//            return ResponseEntity.ok(idService.getPreviousResponse(key));
            return ResponseEntity.ok("OrderPlaced already");
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
        Order order = new Order(oId, dto.getUserId(), dto.getAddress(), epochTime, dto.getItems(), dto.getTotalPrice());
        Order placedOrder = orderRepo.save(order);

        //Delete cart after placing order as it will be empty.
        cartFeignService.emptyCart(dto.getUserId());


        log.info("Order "+oId+"stored in the database.");
        String response = oId + " Placed !!";
        idService.saveIdempotencyKey(new Idempotency(key, response, epochTime));

        //send an event to product service to notify about order product and quantity.
        kafkaService.sendEvent(placedOrder.getItems());
        log.info("Kafka event sent to product service.");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<List<Order>> getOrdersByUserId(int userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        List<Order> orders = template.find(query, Order.class);
        if (orders.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(orders);
    }


}
