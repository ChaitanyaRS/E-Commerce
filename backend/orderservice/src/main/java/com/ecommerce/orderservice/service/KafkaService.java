package com.ecommerce.orderservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static com.ecommerce.orderservice.utility.Constants.*;

import com.ecommerce.orderservice.entity.OrderItem;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String,Object> template;

    public void sendEvent(List<OrderItem> orderItems){
    	Message<List<OrderItem>> message = MessageBuilder
    	        .withPayload(orderItems)
    	        .setHeader(KafkaHeaders.TOPIC, ORDERS_TOPIC)
    	        .setHeader(JsonSerializer.TYPE_MAPPINGS, "orderItemList:com.ecommerce.orderservice.entity.OrderItem")
    	        .build();
        CompletableFuture<SendResult<String, Object>> future = template.send(ORDERS_TOPIC,orderItems);
        future.whenComplete((response,ex)->{
            if(ex == null){
                System.out.println("Send object "+response.getProducerRecord());
                System.out.println("Object sent "+orderItems+" offsetno: "+response.getRecordMetadata().offset());
            }else{
                System.out.println(ex.getMessage());
            }
        });
    }

}
