package com.ecommerce.orderservice.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
import com.ecommerce.orderservice.utility.ProductDto;

@Service
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

    @Transactional
    public ResponseEntity<String> addOrder(String key, OrderDto dto) {

        // Implemented Idempotency key method to prevent duplicate order.
        if (idService.isDuplicate(key)) {
            return ResponseEntity.ok(idService.getPreviousResponse(key));
        }

        int oId = seq.generateSequence("oId");
        System.out.println("oId : " + oId);
        long epochTime = System.currentTimeMillis() / 1000;
        Order order = new Order(oId, dto.getUserId(), dto.getAddress(), epochTime, dto.getItems(), dto.getTotalPrice());
        Order placedOrder = orderRepo.save(order);
        String response = oId + " Placed !!";
        idService.saveIdempotencyKey(new Idempotency(key, response, epochTime));

        //send an event to product service to notify about order product and quantity.
        kafkaService.sendEvent(new ProductDto(p));
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
