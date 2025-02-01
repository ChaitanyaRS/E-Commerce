package com.ecommerce.orderservice.service;


import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import static com.ecommerce.orderservice.utility.Constants.*;


import com.ecommerce.orderservice.utility.ProductDto;

@Service
public class KafkaService {
    private KafkaTemplate<String,Object> template;

    public void sendEvent(ProductDto prod){
        CompletableFuture<SendResult<String, Object>> future = template.send(ORDERS_TOPIC,prod);
        future.whenComplete((response,ex)->{
            if(ex == null){
                System.out.println("Object sent "+prod+" offsetno: "+response.getRecordMetadata().offset());
            }else{
                System.out.println(ex.getMessage());
            }
        });
    }

}
