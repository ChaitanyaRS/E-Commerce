package com.ecommerce.productservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.ecommerce.productservice.utility.OrderItem;
import com.ecommerce.productservice.utility.ProductDto;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import static com.ecommerce.productservice.constants.Constants.*;

import com.ecommerce.productservice.constants.Constants;

@Service
public class KafkaConsumerService {

	@Autowired
	private ProductService prodService;
	

    @KafkaListener(topics = "ORDERS_TOPIC", groupId = "ecommerce-product")
    public void consume(ConsumerRecord<String, Object> record) {
    	Object orderItem = record.value();
    	
        if (orderItem instanceof List) {
            System.out.println("Received Order Items List: " + orderItem);
            List<OrderItem> orderItems = (List<OrderItem>)orderItem;
            prodService.removeProdFromInventory(orderItems);
        } else {
            System.err.println("Unexpected message format: " + orderItem);
           
        }
    }
}
